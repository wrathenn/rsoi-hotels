package com.wrathenn.util.models.loyalty

enum class LoyaltyStatus(val discountPercentage: Int) {
    BRONZE(5),
    SILVER(7),
    GOLD(10),
}

data class Loyalty(
    val id: Long,
    val username: String,
    val reservationCount: Int,
    val status: LoyaltyStatus,
    val discount: Int,
)

enum class LoyaltyReservationCountOperation {
    INCREMENT,
    DECREMENT,
}
