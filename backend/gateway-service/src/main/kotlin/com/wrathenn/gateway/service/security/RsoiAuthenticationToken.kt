package com.wrathenn.gateway.service.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.util.Assert

class RsoiAuthenticationToken(
    private val principal: RsoiPrincipal,
    private var credentials: Any?,
    authorities: List<GrantedAuthority>,
) : AbstractAuthenticationToken(authorities) {

    init {
        super.setAuthenticated(true)
    }

    override fun getPrincipal(): RsoiPrincipal {
        return principal
    }

    override fun getCredentials(): Any? {
        return credentials
    }

    override fun eraseCredentials() {
        credentials = null
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead")
        super.setAuthenticated(false)
    }

}
