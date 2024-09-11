package com.mianeko.rsoi.data.repositories

import android.annotation.SuppressLint
import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Calendar


@SuppressLint("SimpleDateFormat")
class AuthorizationRepository {
    private val formatter: SimpleDateFormat by lazy { SimpleDateFormat("dd/MM/yyyy HH:mm:ss") }
    private val jwkProvider by lazy {
        UrlJwkProvider(URI("http://hotels.rsoi.wrathen.ru/auth/realms/rsoi-hotels/protocol/openid-connect/certs").normalize().toURL())
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(calendar: Calendar): String {
        return formatter.format(calendar.time)
    }
    
    fun fromDate(date: String): Calendar? {
        val calendar = Calendar.getInstance()
        return try {
            calendar.time = formatter.parse(date) ?: return null
            calendar
        } catch (e: Exception) {
            null
        }
    }

    fun parseJwtToken(accessToken: String?): String? {
        val jwt = JWT.decode(accessToken)
        val jwk = jwkProvider.get(jwt.keyId)

        val algorithm = Algorithm.RSA256(jwk.publicKey as java.security.interfaces.RSAPublicKey, null)

        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedToken: DecodedJWT = verifier.verify(accessToken)

        val name = decodedToken.getClaim("name").asString()
        return name
    }
}

data class Principal(
    val userId: String? = null,
    val email: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val roles: List<String> = emptyList()
)
