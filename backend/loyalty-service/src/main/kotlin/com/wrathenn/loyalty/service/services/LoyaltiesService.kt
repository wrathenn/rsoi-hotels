package com.wrathenn.loyalty.service.services

import com.wrathenn.loyalty.service.repositories.LoyaltiesRepository
import com.wrathenn.util.models.loyalty.Loyalty
import com.wrathenn.util.models.loyalty.LoyaltyReservationCountOperation
import com.wrathenn.util.models.loyalty.LoyaltyStatus
import org.jdbi.v3.core.Handle
import org.springframework.stereotype.Component

interface LoyaltiesService {
    context(Handle) fun getLoyalty(username: String): Loyalty
    context(Handle) fun updateReservationCount(
        username: String,
        loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
    ): Loyalty
}

@Component
class LoyaltiesServiceImpl(
    private val loyaltiesRepository: LoyaltiesRepository,
) : LoyaltiesService {
    context(Handle)
    override fun getLoyalty(username: String): Loyalty {
        return loyaltiesRepository.getLoyaltyByUsername(username)
    }

    context(Handle)
    override fun updateReservationCount(
        username: String,
        loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
    ): Loyalty {
        val loyalty = loyaltiesRepository.getLoyaltyByUsername(username, forUpdate = true)
        val newReservationCount = loyalty.reservationCount + when (loyaltyReservationCountOperation) {
            LoyaltyReservationCountOperation.INCREMENT -> +1
            LoyaltyReservationCountOperation.DECREMENT -> -1
        }
        val newStatus = getLoyaltyStatus(newReservationCount)

        return loyaltiesRepository.updateLoyalty(username, newReservationCount, newStatus, newStatus.discountPercentage)
    }
}
