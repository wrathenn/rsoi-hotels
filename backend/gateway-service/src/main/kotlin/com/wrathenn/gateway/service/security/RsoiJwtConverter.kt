package com.wrathenn.gateway.service.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class RsoiJwtConverter(val mapper: ObjectMapper): Converter<Jwt, RsoiAuthenticationToken> {
    data class ResourceAccessItem(
        val roles: List<String>,
    )

    override fun convert(jwt: Jwt): RsoiAuthenticationToken {
        val username: String = mapper.convertValue(jwt.getClaim("email"))

        val resourceAccess = mapper.convertValue<Map<String, ResourceAccessItem>>(jwt.getClaim("resource_access"))
        val allRoles = resourceAccess.values.flatMap { it.roles }
        val role = when {
            allRoles.contains("ADMIN") -> UserRole.ADMIN
            else -> UserRole.USER
        }

        val principal = RsoiPrincipal(
            role = role,
            userClaim = UserClaim(id = username)
        )

        return RsoiAuthenticationToken(
            principal = principal,
            credentials = null,
            authorities = emptyList(),
        )
    }
}
