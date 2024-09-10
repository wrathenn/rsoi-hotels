package com.mianeko.rsoi.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mianeko.rsoi.R
import com.mianeko.rsoi.data.services.AuthorizationService
import com.mianeko.rsoi.databinding.AuthorizationFragmentBinding
import com.mianeko.rsoi.databinding.NavBarLayoutBinding
import com.mianeko.rsoi.ui.navigation.HotelsNavAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.getKoin


class AuthorizationFragment: Fragment(R.layout.authorization_fragment) {

    private var _binding: AuthorizationFragmentBinding? = null
    private val binding get() = _binding!!
    
    private var _navBinding: NavBarLayoutBinding? = null
    private val navBinding get() = _navBinding!!
    
    private var hotelsAdapter: HotelsNavAdapter? = null
    
    private val authService: AuthorizationService by lazy { getKoin().get() }

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
//                    val url = URL(keycloakUrl)
//                    val connection = url.openConnection() as HttpURLConnection
//                    connection.requestMethod = "POST"
//                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
//                    connection.doOutput = true
//                    connection.connectTimeout = 1000
//
//                    val outputStream = OutputStreamWriter(connection.outputStream)
//                    outputStream.write(postData)
//                    outputStream.flush()
//
//                    val responseCode = connection.responseCode
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
                val accessToken = result.accessToken
                val refreshToken = result.refreshToken

                saveTokens(accessToken, refreshToken)
                Toast.makeText(requireContext(), "Sign In Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            apply()
        }
    }

    private fun checkTokens() {
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("access_token", "")
        lifecycleScope.launch(Dispatchers.IO) {
            val header = "Bearer $token"
            val response = authService.getUserInfo(header)
            if (response.isSuccessful) {
                val info = response.body()
                withContext(Dispatchers.Main) {
                    binding.welcomeText.isVisible = true
                    binding.loginGroup.isVisible = false
                    binding.welcomeText.text = "Привет, ${info?.name}!"
                }
            } else {
                Log.e(TAG, "Failed to get user info cause of ${response.errorBody()}, ${response.message()}")
                val refreshToken: String = sharedPref.getString("refresh_token", "") ?: ""
                val refreshResponse = authService.refreshAccessToken(refreshToken)
                if (refreshResponse.isSuccessful) {
                    val tokens = refreshResponse.body()
                    if (tokens != null) {
                        saveTokens(tokens.accessToken, tokens.refreshToken)
                    }
                    val afterRefresh = authService.getUserInfo("Bearer $token")
                    if (afterRefresh.isSuccessful) {
                        val info = response.body()
                        withContext(Dispatchers.Main) {
                            binding.welcomeText.isVisible = true
                            binding.loginGroup.isVisible = false
                            binding.welcomeText.text = "Привет, ${info?.name}!"
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            binding.welcomeText.isVisible = false
                            binding.loginGroup.isVisible = true
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.welcomeText.isVisible = token?.isEmpty() == false
                        binding.welcomeText.text = "Добро пожаловать!"
                        binding.loginGroup.isVisible = token?.isEmpty() != false
                    }
                }
            }
        }
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
