package com.mianeko.rsoi.data.repositories

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mianeko.rsoi.data.entities.KeycloakToken
import com.migcomponents.migbase64.Base64
import java.text.SimpleDateFormat
import java.util.Calendar


class AuthorizationRepository {
    fun isTokenExpired(token: KeycloakToken?): Boolean {
        token?.apply {
            if (tokenExpirationDate == null) return true
            return Calendar.getInstance().after(tokenExpirationDate)
        }
        return true
    }

    fun isRefreshTokenExpired(token: KeycloakToken?): Boolean {
        token?.apply {
            if (refreshTokenExpirationDate == null) return true
            return Calendar.getInstance().after(refreshTokenExpirationDate)
        }
        return true
    }

    @SuppressLint("SimpleDateFormat")
    fun Calendar.formatDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return formatter.format(this.time)
    }

    fun parseJwtToken(jwtToken: String?): Principal {
        jwtToken ?: return Principal()
        jwtToken.apply {
            val splitString = split(".")
            val base64EncodedBody = splitString[1]

            val body = String(Base64.decodeFast(base64EncodedBody))
            val jsonBody = Gson().fromJson(body, JsonObject::class.java)

            val userId = jsonBody.get("sub")?.asString
            val email = jsonBody.get("email")?.asString ?: "n/a"
            val name = jsonBody.get("given_name")?.asString ?: "n/a"
            val surname = jsonBody.get("family_name")?.asString ?: "n/a"
            val roles = jsonBody.get("realm_access")?.asJsonObject?.getAsJsonArray("roles")?.map {it.asString} ?: emptyList()

            return Principal(userId, email, name, surname, roles)
        }
    }
}

data class Principal(
    val userId: String? = null,
    val email: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val roles: List<String> = emptyList()
)
