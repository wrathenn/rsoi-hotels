import { ReservationSchema } from 'api/schemas/reservationSchema.ts'
import React from 'react'
import { formatTemporalDateTime } from 'utils'

export const TicketCard: React.FC<{
  ticket: ReservationSchema.Dto
  onCancelTicketClick: (ticket: ReservationSchema.Dto) => void
}> = x => {
  const dt = Temporal.PlainDateTime.from(x.ticket.date.split(" +")[0])
  
  return (
    <div className="flex items-center justify-between bg-[#2f2a38] text-white w-[1000px] px-4 py-2 rounded-xl" style={{ opacity: x.ticket.status === "CANCELED" ? 0.5 : 1 }}>
      <div className="flex items-center gap-2">
        <div className="w-[80px]">{x.ticket.ticketUid.split("-")[0]}</div>
        <div className="w-[60px]">{x.ticket.flightNumber}</div>
        <div className="w-[140px]">{formatTemporalDateTime(Temporal.PlainDateTime.from(dt))}</div>
        <div className="flex items-center gap-1 w-[400px]">
          <div>{x.ticket.fromAirport}</div>
          <div>-&gt;</div>
          <div>{x.ticket.toAirport}</div>
        </div>
      </div>
      <div className="flex items-center gap-2">
        <div className="w-[140px]">Цена: {x.ticket.price} у.е.</div>
        {
          x.ticket.status === "PAID" ? (
            <button onClick={() => x.onCancelTicketClick(x.ticket)}
                    className="bg-white text-black hover:bg-blue-200 rounded-xl px-2 py-2 w-[130px] text-center">
              Вернуть билет
            </button>
          ) : (
            <div className="bg-white text-black rounded-xl px-2 py-2 w-[130px] text-center">Возврат</div>
          )
        }
      </div>
    </div>
  )
}
