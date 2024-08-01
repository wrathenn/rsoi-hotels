package com.wrathenn.gateway.service.security

enum class UserRole {
    ADMIN,
    USER,
}

data class UserClaim(
    val id: Long,
    val name: String,
)

data class RsoiPrincipal(
    val role: UserRole,
    val userClaim: UserClaim,
)