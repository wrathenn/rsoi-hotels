package com.mianeko.rsoi.data.services

import com.mianeko.rsoi.data.entities.KeycloakToken
import com.mianeko.rsoi.domain.entities.UserInfo
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthorizationService {
    @POST("auth/realms/rsoi-hotels/protocol/openid-connect/token")
    @FormUrlEncoded
    suspend fun grantNewAccessToken(
        @Field("client_secret") clientSecret: String = "5LFJbZK2HYaDkUllxjytBK8GqF4GyTLE",
        @Field("client_id")    clientId: String = "mobile-client",
//        @Field("redirect_uri") uri: String,
        @Field("grant_type")   grantType: String = "password",
        @Field("username")      username: String,
        @Field("password")      password: String,
    ): Response<KeycloakToken>

    @POST("auth/realms/rsoi-hotels/protocol/openid-connect/token")
    @FormUrlEncoded
    suspend fun refreshAccessToken(
        @Field("refresh_token") refreshToken: String,        
        @Field("client_id")    clientId: String = "mobile-client",
        @Field("grant_type")    grantType: String = "refresh_token"
    ): Response<KeycloakToken>

    @GET("auth/realms/rsoi-hotels/protocol/openid-connect/userinfo")
    suspend fun getUserInfo(
        @Header("Authorization") authHeader: String,
    ): Response<UserInfo>
}
