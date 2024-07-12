package com.wrathenn.gateway.service.models

data class UserDto(
    val reservations: List<ReservationInfoDto>,
    val loyalty: BaseLoyaltyShortDto,
)
