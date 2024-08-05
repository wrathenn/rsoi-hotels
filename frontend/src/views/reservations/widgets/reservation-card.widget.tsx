import { ReservationSchema } from 'api/schemas/reservationSchema.ts'
import React from 'react'

export const ReservationCard: React.FC<{
  reservation: ReservationSchema.InfoDto
  onCancelReservationClick: (ticket: ReservationSchema.InfoDto) => void
}> = x => {
  return (
    <div className="flex items-center justify-between bg-[#e39254] text-white w-full px-4 py-2 rounded-xl" style={{ opacity: x.reservation.status === "CANCELED" ? 0.5 : 1 }}>
      <div className="flex items-center gap-2">
        <div className="w-[80px]">{x.reservation.reservationUid.split("-")[0]}</div>
        <div className="w-[200px]">{x.reservation.hotel.name}</div>
        <div className="flex items-center gap-1 w-[400px]">
          <div>От: {x.reservation.startDate?.toString()}</div>
          <div>До: {x.reservation.endDate?.toString()}</div>
        </div>
      </div>
      <div className="flex items-center gap-2">
        {
            x.reservation.payment.price !== undefined ? <div className="">Цена: {x.reservation.payment.price} у.е.</div> : <div>Нет информации об оплате</div> }
        {
          x.reservation.status === "PAID" ? (
            <button onClick={() => x.onCancelReservationClick(x.reservation)}
                    className="bg-white text-black hover:bg-blue-200 rounded-xl px-2 py-2 w-[300px] text-center">
              Отменить бронирование
            </button>
          ) : (
            <div className="bg-white text-black rounded-xl px-2 py-2 w-[300px] text-center">Возврат</div>
          )
        }
      </div>
    </div>
  )
}
