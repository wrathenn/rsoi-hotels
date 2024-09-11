package com.mianeko.rsoi.data.mappers

import com.mianeko.rsoi.data.entities.ApiLoyaltyInfo
import com.mianeko.rsoi.domain.entities.LoyaltyInfo
import com.mianeko.rsoi.domain.entities.LoyaltyStatus

internal class ApiLoyaltyInfoToLoyaltyInfoMapper: (ApiLoyaltyInfo) -> LoyaltyInfo {
    override fun invoke(loyalty: ApiLoyaltyInfo): LoyaltyInfo {
        val status = when (loyalty.status) {
            LoyaltyStatus.BRONZE.text -> LoyaltyStatus.BRONZE
            LoyaltyStatus.SILVER.text -> LoyaltyStatus.SILVER
            LoyaltyStatus.GOLD.text -> LoyaltyStatus.GOLD
            else -> throw IllegalStateException("Got invalid loyalty status")
        }
        return LoyaltyInfo(
            status = status,
            discount = loyalty.discount,
            reservationCount = loyalty.reservationCount,
        )
    }
}
