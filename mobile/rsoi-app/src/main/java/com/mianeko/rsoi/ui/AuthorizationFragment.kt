package com.mianeko.rsoi.ui

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mianeko.rsoi.R
import com.mianeko.rsoi.data.entities.KeycloakToken
import com.mianeko.rsoi.data.repositories.AuthorizationRepository
import com.mianeko.rsoi.data.services.AuthorizationService
import com.mianeko.rsoi.databinding.AuthorizationFragmentBinding
import com.mianeko.rsoi.databinding.NavBarLayoutBinding
import com.mianeko.rsoi.ui.navigation.HotelsNavAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.getKoin
import kotlin.time.Duration.Companion.milliseconds


class AuthorizationFragment: Fragment(R.layout.authorization_fragment) {

    private var _binding: AuthorizationFragmentBinding? = null
    private val binding get() = _binding!!
    
    private var _navBinding: NavBarLayoutBinding? = null
    private val navBinding get() = _navBinding!!
    
    private var hotelsAdapter: HotelsNavAdapter? = null
    
    private val authService: AuthorizationService by lazy { getKoin().get() }

    private val authRepo: AuthorizationRepository by lazy { getKoin().get() }
    
    fun setAdapter(adapter: HotelsNavAdapter) {
        hotelsAdapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthorizationFragmentBinding.inflate(inflater, container, false)
        _navBinding = NavBarLayoutBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkTokens()
        binding.signInBtn.setOnClickListener {
            val username = binding.loginValue.text.toString().trim()
            val password = binding.passwordValue.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                Log.d(TAG, "signing in with $username and $password")
                authenticateUser(username, password)
            } else {
                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.loginValue.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
        binding.passwordValue.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        navBinding.hotelsNavButton.setOnClickListener {
            hotelsAdapter?.showHotels()
        }
        navBinding.accountButton.setOnClickListener {
            hotelsAdapter?.authorize()
        }
        navBinding.reservationsNavButton.setOnClickListener {
            hotelsAdapter?.showReservations()
        }
        navBinding.loyaltyNavButton.setOnClickListener {
            hotelsAdapter?.showLoyaltyInfo()
        }
    }

    private fun authenticateUser(username: String, password: String) {
        val keycloakUrl = "http://hotels.rsoi.wrathen.ru/auth/realms/rsoi-hotels/protocol/openid-connect/token"
        val clientId = "mobile-client"
        val clientSecret = "5LFJbZK2HYaDkUllxjytBK8GqF4GyTLE"
        val grantType = "password"
        
        val postData = "client_id=$clientId&client_secret=$clientSecret&grant_type=$grantType&username=$username&password=$password"

        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val response = authService.grantNewAccessToken(
                        username = username,
                        password = password,
                    )
                    if (!response.isSuccessful) {
                        Log.e(TAG, "getHotels request failed: code = ${response.code()}, message = ${response.message()}")
                        null
                    } else {
                        Log.d(TAG, "response is $response")
                        response.body()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "An exception occurred during request")
                    e.printStackTrace()
                    null
                }
            }

            if (result != null) {
                saveTokens(result)
                Toast.makeText(requireContext(), "Вход осуществлен успешно", Toast.LENGTH_SHORT).show()
                withContext(Dispatchers.IO) {
                    val principal = authRepo.parseJwtToken(result.accessToken)
                    withContext(Dispatchers.Main) {
                        with(binding) {
                            welcomeText.isVisible = true
                            loginGroup.isVisible = false
                            welcomeText.text = "Привет, ${principal}!"
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Не удалось войти в систему", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveTokens(keycloakToken: KeycloakToken) {
        val createTime = SystemClock.elapsedRealtime().milliseconds
        Log.d(TAG, "Saving tokens, expires in is ${keycloakToken.expiresIn}, refresh expires in ${keycloakToken.refreshExpiresIn}")
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("access_token", keycloakToken.accessToken)
            putString("refresh_token", keycloakToken.refreshToken)
            putString("id_token", keycloakToken.idToken)
            keycloakToken.expiresIn?.let { putLong("expires_in", it.toLong()) }
            keycloakToken.refreshExpiresIn?.let { putLong("refresh_expires_in", it.toLong()) }
            putLong("creation_time", createTime.inWholeSeconds)
            apply()
        }
    }
    
    private fun deleteTokens() {
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            remove("access_token")
            remove("refresh_token")
            remove("id_token")
            remove("expires_in")
            remove("refresh_expires_in")
            remove("creation_time")
            apply()
        }
    }

    private fun checkTokens() {
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) ?: return
        val token: String?
        val expiresIn: Long?
        val refresh: String?
        val refreshExpiresIn: Long?
        val creationTime: Long?
        try {
            with(sharedPref) {
                token = getString("access_token", "")?.takeIf { it.isNotEmpty() }
                expiresIn = getLong("expires_in", -1L)
                refresh = getString("refresh_token", "")?.takeIf { it.isNotEmpty() }
                refreshExpiresIn = getLong("refresh_expires_in", -1L)
                creationTime = getLong("creation_time", -1L)
            }
        } catch (e: Exception) {
            deleteTokens()
            binding.welcomeText.isVisible = false
            binding.loginGroup.isVisible = true
            return
        }
        val now = SystemClock.elapsedRealtime().milliseconds.inWholeSeconds
        Log.d(TAG, "expiresIn = $expiresIn, refExpIn = $refreshExpiresIn")
        when {
            token != null && isNotExpired(expiresIn, creationTime, now) -> {
                Log.d(TAG, "Access token is alive")
                lifecycleScope.launch(Dispatchers.IO) { 
                    val principal = authRepo.parseJwtToken(token)
                    withContext(Dispatchers.Main) {
                        with (binding) {
                            welcomeText.isVisible = true
                            loginGroup.isVisible = false
                            welcomeText.text = "Привет, ${principal}!"
                        }
                    }
                }
            }
            refresh != null && isNotExpired(refreshExpiresIn, creationTime, now) -> {
                Log.d(TAG, "Access token is expired but refresh is ok")
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = authService.refreshAccessToken(refresh)
                    if (result.isSuccessful) {
                        Log.d(TAG, "Refreshed token successfully")
                        val principal = authRepo.parseJwtToken(result.body()?.accessToken)
                        withContext(Dispatchers.Main) {
                            with(binding) {
                                welcomeText.isVisible = true
                                loginGroup.isVisible = false
                                welcomeText.text = "Привет, ${principal}!"
                            }
                        }
                        result.body()?.let { saveTokens(it) }
                    } else {
                        Log.d(TAG, "Could not refresh token")
                        deleteTokens()
                        withContext(Dispatchers.Main) {
                            binding.welcomeText.isVisible = false
                            binding.loginGroup.isVisible = true
                        }
                    }
                }
            }
            else -> {
                Log.d(TAG, "All tokens expired or none have ever existed")
                deleteTokens()
                binding.welcomeText.isVisible = false
                binding.loginGroup.isVisible = true
            }
        }
    }
    
    private fun isNotExpired(expiresIn: Long?, creationTime: Long?, now: Long): Boolean {
        Log.d(TAG, "IsExpired for $expiresIn, $creationTime, $now")
        return expiresIn != null && expiresIn != -1L && creationTime != null && creationTime != -1L 
                && (now - creationTime <= expiresIn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        private const val TAG = "AuthorizationFragment"
        fun newInstance(): AuthorizationFragment {
            return AuthorizationFragment()
        }
    }
}
