package com.wrathenn.gateway.service.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthContext {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getCurrentPrincipal(): RsoiPrincipal {
        val authentication = SecurityContextHolder.getContext().authentication
        logger.info("Authentication: $authentication")

        val principal = authentication.principal
        logger.info("Principal: $principal")

        if (principal !is RsoiPrincipal) {
            throw IllegalStateException("Bad principal")
        }

        return principal
    }
}
