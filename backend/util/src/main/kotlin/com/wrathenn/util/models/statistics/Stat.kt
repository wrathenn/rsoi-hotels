package com.wrathenn.util.models.statistics

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wrathenn.util.models.loyalty.LoyaltyReservationCountOperation
import java.time.Instant

data class Stat(
    val id: Long,
    val ts: Instant,
    val data: StatData,
)

data class StatTemplate<D: StatData>(
    val ts: Instant,
    val data: D,
)

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(value = StatData.LoyaltyFailedUpdate::class, name = "LOYALTY_FAILED_UPDATE"),
    JsonSubTypes.Type(value = StatData.LoyaltyRestoredUpdate::class, name = "LOYALTY_RESTORED_UPDATE"),
)
sealed class StatData(type: Type) {
    enum class Type {
        LOYALTY_FAILED_UPDATE,
        LOYALTY_RESTORED_UPDATE,
    }

    data class LoyaltyFailedUpdate(
        val username: String,
        val loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
    ) : StatData(Type.LOYALTY_FAILED_UPDATE)

    data class LoyaltyRestoredUpdate(
        val username: String,
        val loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
    ) : StatData(Type.LOYALTY_RESTORED_UPDATE)
}
