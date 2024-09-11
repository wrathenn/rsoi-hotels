package com.mianeko.rsoi.data.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.mianeko.rsoi.data.entities.KeycloakToken


interface TokenStorage {
    fun getStoredAccessToken(): KeycloakToken?
    fun storeAccessToken(token: KeycloakToken)
    fun hasAccessToken(): Boolean
    fun removeAccessToken()
}

class SharedPreferencesTokenStorage(val prefs: SharedPreferences, val gson: Gson) : TokenStorage {
    val ACCESS_TOKEN_PREFERENCES_KEY = "OpenIDAccessToken"

    override fun getStoredAccessToken(): KeycloakToken? {
        val tokenStr = prefs.getString(ACCESS_TOKEN_PREFERENCES_KEY, null)
        return if (tokenStr == null) null
        else gson.fromJson(tokenStr, KeycloakToken::class.java)
    }

    override fun storeAccessToken(token: KeycloakToken) {
        prefs.edit()
            .putString(ACCESS_TOKEN_PREFERENCES_KEY, gson.toJson(token))
            .apply()
    }

    override fun hasAccessToken(): Boolean {
        return prefs.contains(ACCESS_TOKEN_PREFERENCES_KEY)
    }

    override fun removeAccessToken() {
        prefs.edit()
            .remove(ACCESS_TOKEN_PREFERENCES_KEY)
            .apply()
    }
}
