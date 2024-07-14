package com.wrathenn.gateway.service.models.bnuuy

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.wrathenn.util.models.loyalty.LoyaltyReservationCountOperation

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(value = KafkaRequest.LoyaltyUpdateReservationCountRequest::class, name = "LOYALTY_UPDATE_COUNT"),
)
sealed class KafkaRequest(
    val type: RequestType
) {
    enum class RequestType {
        LOYALTY_UPDATE_COUNT,
    }

    data class LoyaltyUpdateReservationCountRequest(
        val username: String,
        val loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
    ) : KafkaRequest(RequestType.LOYALTY_UPDATE_COUNT)
}


