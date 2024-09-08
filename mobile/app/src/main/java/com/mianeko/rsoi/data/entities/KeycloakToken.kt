package com.mianeko.rsoi.data.entities

import com.google.gson.annotations.SerializedName
import java.util.Calendar

data class KeycloakToken(
    @SerializedName("access_token")               var accessToken: String = "",
    @SerializedName("expires_in")                 var expiresIn: Int? = null,
    @SerializedName("refresh_expires_in")         var refreshExpiresIn: Int? = null,
    @SerializedName("refresh_token")              var refreshToken: String = "",
    @SerializedName("token_type")                 var tokenType: String? = null,
    @SerializedName("id_token")                   var idToken: String? = null,
    @SerializedName("not-before-policy")          var notBeforePolicy: Int? = null,
    @SerializedName("session_state")              var sessionState: String? = null,
    @SerializedName("token_expiration_date")      var tokenExpirationDate: Calendar? = null,
    @SerializedName("refresh_expiration_date")    var refreshTokenExpirationDate: Calendar? = null
)
