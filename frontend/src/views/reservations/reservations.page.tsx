import { ReservationSchema } from 'api/schemas/reservationSchema.ts'
import { observer } from 'mobx-react-lite'
import React, { useEffect } from 'react'
import { ReservationsVm } from "views/reservations/reservations.vm.ts";
import { ReservationCard } from "views/reservations/widgets/reservation-card.widget.tsx";
import { CancelReservationModal } from "views/reservations/widgets/cancel-reservation.modal.tsx";

export const ReservationsPage: React.FC = observer(() => {
  useEffect(() => {
    void ReservationsVm.loadData()
  }, [])
  
  const onCancelTicketClick = (ticket: ReservationSchema.InfoDto) => {
      ReservationsVm.showCancelReservationModal(ticket)
  }

  return (
    <div className="flex flex-col gap-10">
      <h1>Билеты</h1>
      { ReservationsVm.reservations.length === 0 && <h2>У вас нет бронирований!</h2> }
      <div className="flex flex-col gap-2 px-10">
        {
            ReservationsVm.reservations
            .map(r => <ReservationCard reservation={r} key={r.reservationUid} onCancelReservationClick={onCancelTicketClick}/>)
        }
      </div>
      <CancelReservationModal isModalShowed={ReservationsVm.isCancelReservationModalShowed}
                      hideModal={ReservationsVm.hideCancelReservationModal}
                      ticket={ReservationsVm.selectedReservation}
                      onCancelClick={t => ReservationsVm.cancelReservation(t.reservationUid)}
      />
    </div>
  )
})
