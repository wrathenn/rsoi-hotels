import { HotelSchema } from 'api/schemas/hotelSchema.ts'
import React, { useState } from 'react'
import { Modal, ModalProps } from 'widgets/modal/modal.widget.tsx'


type ReserveHotelModalProps = ModalProps & {
  hotel: HotelSchema.Dto | null
  onBuyClick: (hotel: HotelSchema.Dto, fromDate: Temporal.PlainDate, toDate: Temporal.PlainDate) => void
}

export function formatPlainDate(value: Temporal.PlainDate): string {
  function toNLen(v: number, n: number): string {
    const len = v.toString().length
    if (len >= n) return v.toString()
    const prefix = "0".repeat(n - len)
    return prefix + v.toString()
  }

  return `${value.year}-${toNLen(value.month, 2)}-${toNLen(value.day, 2)}`
}

export const ReserveHotelModal: React.FC<ReserveHotelModalProps> = (x) => {
  if (!x.hotel) return <></>

  const [fromDate, setFromDate] = useState<Temporal.PlainDate>(Temporal.Now.plainDateISO())
  const [toDate, setToDate] = useState<Temporal.PlainDate>(Temporal.Now.plainDateISO().add({ days: 7 }))

  return (
    <Modal isModalShowed={x.isModalShowed} hideModal={x.hideModal}>
      <div className="flex flex-col gap-4">
        <div className="text-lg font-bold w-full text-center">Забронировать отель</div>
        <div className="flex items-center gap-1">
          <div>Название отеля:</div>
          <div>{x.hotel.name}</div>
        </div>
        <div className="flex items-center gap-1">
          <div>Адрес:</div>
          <div>{x.hotel.country}, {x.hotel.city}, {x.hotel.address}</div>
        </div>
        <div className="flex items-center gap-1">
          <div>Цена в день:</div>
          <div>{x.hotel.price}</div>
        </div>
        {x.hotel.stars ? <div className="flex items-center gap-1">
          <div>Звезд:</div>
          <div>{x.hotel.stars}</div>
        </div> : <></>}

        <input className={"text-black"} type={"date"} onChange={event => {
          const value = event.target.value
          if (value) {
            setFromDate(Temporal.PlainDate.from(value))
          }
          console.log(Temporal.PlainDate.from(value))
        }} value={formatPlainDate(fromDate)}/>

        <input className={"text-black"} type={"date"} onChange={event => {
          const value = event.target.value
          if (value) {
            setToDate(Temporal.PlainDate.from(value))
          }
        }} value={formatPlainDate(toDate)}/>

        <button onClick={() => {
          if (x.hotel) {
            x.onBuyClick(x.hotel, fromDate, toDate)
          }
        }}>
          Купить
        </button>
      </div>
    </Modal>
  )
}