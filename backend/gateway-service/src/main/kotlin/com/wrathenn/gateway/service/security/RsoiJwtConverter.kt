package com.wrathenn.gateway.service.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class RsoiJwtConverter(val mapper: ObjectMapper): Converter<Jwt, RsoiAuthenticationToken> {

    override fun convert(jwt: Jwt): RsoiAuthenticationToken {
        val principal = RsoiPrincipal(
            role = mapper.convertValue(jwt.getClaim("role")),
            userClaim = mapper.convertValue(jwt.getClaim("user")),
        )

        return RsoiAuthenticationToken(
            principal = principal,
            credentials = null,
            authorities = emptyList(),
        )
    }

}
