import { HotelSchema } from 'api/schemas/hotelSchema.ts'
import React from 'react'
import { formatTemporalDateTime } from 'utils'

export const HotelCard: React.FC<{
  hotel: HotelSchema.Dto
  onReserveHotelClick: (hotel: HotelSchema.Dto) => void
}> = x => {
  const dt = Temporal.PlainDateTime.from(x.hotel.date.split(" +")[0])
  
  return (
    <div className="flex items-center justify-between bg-[#2f2a38] text-white w-[1000px] px-4 py-2 rounded-xl">
      <div className="flex items-center gap-2">
        <div className="w-[60px]">{x.hotel.flightNumber}</div>
        <div className="w-[140px]">{formatTemporalDateTime(Temporal.PlainDateTime.from(dt))}</div>
        <div className="flex items-center gap-1 w-[400px]">
          <div>{x.hotel.fromAirport}</div>
          <div>-&gt;</div>
          <div>{x.hotel.toAirport}</div>
        </div>
      </div>
      <div className="flex items-center gap-2">
        <div className="w-[140px]">Цена: {x.hotel.price} у.е.</div>
        <button onClick={() => x.onReserveHotelClick(x.hotel)}
                className="bg-white text-black hover:bg-blue-200 rounded-xl px-2 py-2">
          Купить билет
        </button>
      </div>
    </div>
  )
}