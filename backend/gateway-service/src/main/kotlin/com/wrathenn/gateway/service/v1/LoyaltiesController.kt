package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.LoyaltyDto
import com.wrathenn.gateway.service.services.LoyaltyService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/loyalty")
@CrossOrigin
class LoyaltiesController(
    private val loyaltyService: LoyaltyService
) {
    @GetMapping
    fun getLoyalty(@RequestHeader("X-User-Name") username: String): LoyaltyDto {
        return loyaltyService.getLoyalty(username)
    }
}
