package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.LoyaltyDto
import com.wrathenn.gateway.service.security.AuthContext
import com.wrathenn.gateway.service.services.LoyaltyService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/loyalty")
@CrossOrigin
class LoyaltiesController(
    private val loyaltyService: LoyaltyService,
    private val authContext: AuthContext,
) {
    @GetMapping
    fun getLoyalty(): LoyaltyDto {
        val username = authContext.getCurrentPrincipal().userClaim.id
        return loyaltyService.getLoyalty(username)
    }
}
