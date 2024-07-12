package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.UserDto
import com.wrathenn.gateway.service.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/me")
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun getUserInfo(@RequestHeader("X-User-Name") username: String): UserDto {
        return userService.getUserInfo(username)
    }
}
