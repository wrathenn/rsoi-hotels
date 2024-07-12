package com.wrathenn.loyalty.service.repositories

import com.wrathenn.loyalty.service.repositories.entities.LoyaltyEntity
import com.wrathenn.loyalty.service.repositories.entities.LoyaltyEntity.Converters.toModel
import com.wrathenn.util.exceptions.ResourceNotFoundException
import com.wrathenn.util.models.loyalty.Loyalty
import com.wrathenn.util.models.loyalty.LoyaltyStatus
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import org.springframework.stereotype.Component
import java.lang.Exception

interface LoyaltiesRepository {
    context(Handle) fun getLoyaltyByUsername(username: String, forUpdate: Boolean = false): Loyalty
    context(Handle) fun updateLoyalty(
        username: String,
        reservationCount: Int,
        status: LoyaltyStatus,
        discount: Int,
    ): Loyalty
}

@Component
class LoyaltiesRepositoryImpl : LoyaltiesRepository {
    context(Handle)
    override fun getLoyaltyByUsername(username: String, forUpdate: Boolean): Loyalty {
        val forUpdatePart = if (forUpdate) "for update" else ""

        return select("""
            select
              id,
              username,
              reservation_count,
              status,
              discount
            from loyalty.loyalty
            where username = :username
            $forUpdatePart
        """.trimIndent())
            .bind("username", username)
            .mapTo<LoyaltyEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ResourceNotFoundException("Карта лояльности для пользователя $username не найдена")
    }

    context(Handle) override fun updateLoyalty(
        username: String,
        reservationCount: Int,
        status: LoyaltyStatus,
        discount: Int,
    ): Loyalty {
        return createQuery("""
            update loyalty.loyalty
            set reservation_count = :reservationCount,
                status = :status,
                discount = :discount
            where username = :username
            returning id, username, reservation_count, status, discount
        """.trimIndent())
            .bind("reservationCount", reservationCount)
            .bind("status", status)
            .bind("discount", discount)
            .bind("username", username)
            .mapTo<LoyaltyEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ResourceNotFoundException("Карта лояльности для пользователя $username не найдена")
    }
}
