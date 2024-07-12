package com.wrathenn.gateway.service.models

import com.wrathenn.util.models.loyalty.LoyaltyStatus

sealed interface BaseLoyaltyDto

data class LoyaltyDto(
    val status: LoyaltyStatus,
    val discount: Int,
    val reservationCount: Int,
) : BaseLoyaltyDto

data object EmptyLoyaltyDto : BaseLoyaltyDto

sealed interface BaseLoyaltyShortDto

data class LoyaltyShortDto(
    val status: LoyaltyStatus,
    val discount: Int,
) : BaseLoyaltyShortDto

data object EmptyLoyaltyShortDto : BaseLoyaltyShortDto
