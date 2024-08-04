import { ReservationSchema } from 'api/schemas/reservationSchema.ts'
import { observer } from 'mobx-react-lite'
import React, { useEffect } from 'react'
import { Navigate } from 'react-router-dom'
import { UserStore } from 'stores/user.store.ts'
import { TicketsVm } from 'views/tickets/tickets.vm.ts'
import { CancelTicketModal } from 'views/tickets/widgets/cancel-ticket.modal.tsx'
import { TicketCard } from 'views/tickets/widgets/ticket-card.widget.tsx'

export const ReservationsPage: React.FC = observer(() => {
  useEffect(() => {
    void TicketsVm.loadData()
  }, [])
  
  const onCancelTicketClick = (ticket: ReservationSchema.Dto) => {
      TicketsVm.showCancelTicketModal(ticket)
  }

  return (
    <div className="flex flex-col gap-10">
      { !UserStore.isAuth && <Navigate to={"/"}/> }
      <h1>Билеты</h1>
      { TicketsVm.tickets.length === 0 && <h2>У вас нет билетов</h2> }
      <div className="flex flex-col gap-2 px-10">
        {
          TicketsVm.tickets
            .map(t => <TicketCard ticket={t} key={t.ticketUid} onCancelTicketClick={onCancelTicketClick}/>)
        }
      </div>
      <CancelTicketModal isModalShowed={TicketsVm.isCancelTicketModalShowed}
                      hideModal={TicketsVm.hideCancelTicketModal}
                      ticket={TicketsVm.selectedTicket}
                      onCancelClick={t => TicketsVm.cancelTicket(t.ticketUid)}
      />
    </div>
  )
})
