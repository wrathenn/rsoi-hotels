import { ReservationsApi } from 'api/endpoints/reservations.ts'
import { HotelSchema } from 'api/schemas/hotelSchema.ts'
import { observer } from 'mobx-react-lite'
import React, { useEffect } from 'react'
import { HotelsVm } from 'views/hotels/hotels.vm.ts'
import { HotelCard } from "views/hotels/widgets/hotel-card.widget.tsx";
import { ReserveHotelModal } from "views/hotels/widgets/reserve-hotel.tsx";

export const HotelsPage: React.FC = observer(() => {
  useEffect(() => {
    void HotelsVm.loadData();
  }, [])

  const onReserveHotelClick = (hotel: HotelSchema.Dto) => {
      HotelsVm.showReserveHotelModal(hotel)
  }

  return (
    <div className="flex flex-col gap-10">
      <h1>Забронировать отель</h1>
      <div className="flex flex-col gap-2 px-10">
        {
          HotelsVm.hotels
            .map(h => <HotelCard hotel={h} key={h.hotelUid} onReserveHotelClick={onReserveHotelClick}/>)
        }
      </div>
      <ReserveHotelModal isModalShowed={HotelsVm.isReserveHotelModalShowed}
                      hideModal={HotelsVm.hideReserveHotelModal}
                      hotel={HotelsVm.selectedHotel}
                      onBuyClick={(h, fd, td) => {
                        ReservationsApi.reserveHotel({
                          hotelUid: h.hotelUid,
                          startDate: fd,
                          endDate: td,
                        }).then(() => HotelsVm.hideReserveHotelModal())
                      }}
      />
    </div>
  )
})
