package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.UserDto
import com.wrathenn.gateway.service.security.AuthContext
import com.wrathenn.gateway.service.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/me")
@CrossOrigin
class UserController(
    private val userService: UserService,
    private val authContext: AuthContext,
) {
    @GetMapping
    fun getUserInfo(): UserDto {
        val username = authContext.getCurrentPrincipal().userClaim.id
        return userService.getUserInfo(username)
    }
}
