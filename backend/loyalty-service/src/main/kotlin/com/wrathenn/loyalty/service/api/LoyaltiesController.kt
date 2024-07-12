package com.wrathenn.loyalty.service.api

import com.wrathenn.loyalty.service.services.LoyaltiesService
import com.wrathenn.util.db.transact
import com.wrathenn.util.models.loyalty.Loyalty
import com.wrathenn.util.models.loyalty.LoyaltyReservationCountOperation
import org.jdbi.v3.core.Jdbi
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/loyalties")
class LoyaltiesController(
    private val jdbi: Jdbi,
    private val loyaltiesService: LoyaltiesService,
) {
    @GetMapping
    fun getLoyalty(@RequestHeader("X-User-Name") username: String): Loyalty = jdbi.transact {
        loyaltiesService.getLoyalty(username)
    }

    @PutMapping("/reservationCount")
    fun updateReservationCount(
        @RequestHeader("X-User-Name") username: String,
        @RequestBody loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
    ): Loyalty = jdbi.transact {
        loyaltiesService.updateReservationCount(username, loyaltyReservationCountOperation)
    }
}
