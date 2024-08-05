import { ReservationSchema } from 'api/schemas/reservationSchema.ts'
import React from 'react'
import { Modal, ModalProps } from 'widgets/modal/modal.widget.tsx'

type CancelTicketModalProps = ModalProps & {
  ticket: ReservationSchema.InfoDto | null
  onCancelClick: (ticket: ReservationSchema.InfoDto) => void
}

export const CancelReservationModal: React.FC<CancelTicketModalProps> = x => {
  return (
    <Modal isModalShowed={x.isModalShowed} hideModal={x.hideModal}>
      <div className="flex flex-col justify-between gap-5">
        <div className="text-lg font-bold w-full text-center">Вы точно хотите вернуть билет?</div>
        <div className="flex items-center justify-center gap-4">
          <button onClick={x.hideModal} className="w-[100px]">
            Нет
          </button>
          <button onClick={() => {
            if (x.ticket) {
              x.onCancelClick(x.ticket)
            }
          }} className="w-[100px]">
            Да
          </button>
        </div>
      </div>
    </Modal>
)
}
