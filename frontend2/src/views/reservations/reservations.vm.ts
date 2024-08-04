import { ReservationsApi } from 'api/endpoints/reservations.ts'
import { ReservationSchema } from 'api/schemas/reservationSchema.ts'
import { makeAutoObservable, reaction } from 'mobx'

export const ReservationsVm = new class {
  private _reservations: ReservationSchema.InfoDto[] = []
  get reservations() { return [...this._reservations] }

  private _isCancelReservationModalShowed = false
  get isCancelReservationModalShowed() { return this._isCancelReservationModalShowed }

  selectedReservation: ReservationSchema.Dto | null = null
  readonly showCancelReservationModal = (reservation: ReservationSchema.Dto) => {
    this._isCancelReservationModalShowed = true
    this.selectedReservation = reservation
  }
  readonly hideCancelReservationModal = () => {
    this._isCancelReservationModalShowed = false
    this.selectedReservation = null
  }
  
  async cancelReservation(uid: string) {
    await ReservationsApi.cancelReservation(uid)
    this._reservations[this._reservations.findIndex(t => t.reservationUid === uid)].status = "CANCELED"
    this.hideCancelReservationModal()
  }
  
  constructor() {
    makeAutoObservable(this)
  }
  
  async loadData() {
    this._reservations = await ReservationsApi.getAllInfos()
  }
}
