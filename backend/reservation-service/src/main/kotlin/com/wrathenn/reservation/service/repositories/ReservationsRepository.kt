package com.wrathenn.reservation.service.repositories

import com.wrathenn.reservation.service.repositories.entities.ReservationEntity
import com.wrathenn.reservation.service.repositories.entities.ReservationEntity.Converters.toModel
import com.wrathenn.reservation.service.repositories.entities.ReservationWithHotelEntity
import com.wrathenn.reservation.service.repositories.entities.ReservationWithHotelEntity.Converters.toModel
import com.wrathenn.util.exceptions.ApiException
import com.wrathenn.util.exceptions.ResourceNotFoundException
import com.wrathenn.util.models.reservation.Reservation
import com.wrathenn.util.models.reservation.ReservationCreate
import com.wrathenn.util.models.reservation.ReservationStatus
import com.wrathenn.util.models.reservation.ReservationWithHotel
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.bindKotlin
import org.jdbi.v3.core.kotlin.mapTo
import org.springframework.stereotype.Component
import java.util.*

interface ReservationsRepository {
    context(Handle) fun getReservation(reservationUid: UUID): ReservationWithHotel
    context(Handle) fun getReservationsByUsername(username: String): List<ReservationWithHotel>
    context(Handle) fun createReservation(reservationCreate: ReservationCreate): Reservation
    context(Handle) fun cancelReservation(reservationUid: UUID): Reservation
}

@Component
class ReservationsRepositoryImpl : ReservationsRepository {
    context(Handle) override fun getReservation(reservationUid: UUID): ReservationWithHotel {
        return select("""
            select
              r.id as r_id,
              r.reservation_uid as r_reservation_uid,
              r.username as r_username,
              r.payment_uid as r_payment_uid,
              r.hotel_id as r_hotel_id,
              r.status as r_status,
              r.start_date as r_start_date,
              r.end_date as r_end_date,
                          
              h.id as h_id,
              h.hotel_uid as h_hotel_uid,
              h.name as h_name,
              h.country as h_country,
              h.city as h_city,
              h.address as h_address,
              h.stars as h_stars,
              h.price as h_price
            from reservation.reservation r
              join reservation.hotel h on (r.hotel_id = h.id)
            where reservation_uid = :reservationUid
        """.trimIndent())
            .bind("reservationUid", reservationUid)
            .mapTo<ReservationWithHotelEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ResourceNotFoundException("Бронь с uid = $reservationUid не найдена")
    }

    context(Handle)
    override fun getReservationsByUsername(username: String): List<ReservationWithHotel> {
        return select("""
            select
              r.id as r_id,
              r.reservation_uid as r_reservation_uid,
              r.username as r_username,
              r.payment_uid as r_payment_uid,
              r.hotel_id as r_hotel_id,
              r.status as r_status,
              r.start_date as r_start_date,
              r.end_date as r_end_date,
                          
              h.id as h_id,
              h.hotel_uid as h_hotel_uid,
              h.name as h_name,
              h.country as h_country,
              h.city as h_city,
              h.address as h_address,
              h.stars as h_stars,
              h.price as h_price
            from reservation.reservation r
              join reservation.hotel h on (r.hotel_id = h.id)
            where username = :username
        """.trimIndent())
            .bind("username", username)
            .mapTo<ReservationWithHotelEntity>()
            .list()
            .map { it.toModel() }
    }

    context(Handle) override fun createReservation(reservationCreate: ReservationCreate): Reservation {
        return createQuery("""
            insert into reservation.reservation(
                reservation_uid,
                username,
                payment_uid,
                hotel_id,
                status,
                start_date,
                end_date
            ) values (
                gen_random_uuid(),
                :username,
                :paymentUid,
                :hotelId,
                :status,
                :startDate,
                :endDate
            )
            returning id, reservation_uid, username, payment_uid, hotel_id, status, start_date, end_date
        """.trimIndent())
            .bindKotlin(reservationCreate)
            .mapTo<ReservationEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ApiException("Ошибка при создании брони $reservationCreate")
    }

    context(Handle) override fun cancelReservation(reservationUid: UUID): Reservation {
        return createQuery("""
            update reservation.reservation
            set status = :status
            where reservation_uid = :reservationUid
            returning id, reservation_uid, username, payment_uid, hotel_id, status, start_date, end_date
        """.trimIndent())
            .bind("status", ReservationStatus.CANCELED)
            .bind("reservationUid", reservationUid)
            .mapTo<ReservationEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ApiException("Ошибка при отмене брони $reservationUid")
    }
}
