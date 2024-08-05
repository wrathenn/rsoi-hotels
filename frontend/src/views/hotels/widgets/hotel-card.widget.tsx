import { HotelSchema } from 'api/schemas/hotelSchema.ts'
import React from 'react'
import { formatTemporalDateTime } from 'utils'

export const HotelCard: React.FC<{
  hotel: HotelSchema.Dto
  onReserveHotelClick: (hotel: HotelSchema.Dto) => void
}> = x => {
  return (
    <div className="flex items-center justify-between bg-[#e39254] text-white w-full px-4 py-2 rounded-xl">
      <div className="flex items-center gap-2">
        <div className="w-[240px]">{x.hotel.name}</div>
        <div className="w-[240px]">{`${x.hotel.country}, ${x.hotel.city}`}</div>
      </div>
      <div className="flex items-center gap-2">
        <div className="w-[120px]">{`Звезд: ${x.hotel.stars}`}</div>
        <div className="w-[140px]">В день: {x.hotel.price} у.е.</div>
        <button onClick={() => x.onReserveHotelClick(x.hotel)}
                className="bg-white text-black hover:bg-blue-200 rounded-xl px-2 py-2">
          Забронировать
        </button>
      </div>
    </div>
  )
}