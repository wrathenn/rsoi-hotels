package com.wrathenn.gateway.service.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class RsoiJwtConverter(val mapper: ObjectMapper): Converter<Jwt, RsoiAuthenticationToken> {
    override fun convert(jwt: Jwt): RsoiAuthenticationToken {
        val username: String = mapper.convertValue(jwt.getClaim("email"))

        val principal = RsoiPrincipal(
            role = UserRole.USER,
            userClaim = UserClaim(id = username)
        )

        return RsoiAuthenticationToken(
            principal = principal,
            credentials = null,
            authorities = emptyList(),
        )
    }
}
