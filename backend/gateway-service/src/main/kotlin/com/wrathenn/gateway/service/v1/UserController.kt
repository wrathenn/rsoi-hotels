package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.UserDto
import com.wrathenn.gateway.service.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/me")
@CrossOrigin
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun getUserInfo(@RequestHeader("X-User-Name") username: String): UserDto {
        return userService.getUserInfo(username)
    }
}
