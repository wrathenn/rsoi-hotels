import { ReservationsApi } from 'api/endpoints/reservations.ts'
import { ReservationSchema } from 'api/schemas/reservationSchema.ts'
import { makeAutoObservable, reaction } from 'mobx'
import { UserStore } from 'stores/user.store.ts'

export const ReservationsVm = new class {
  private _tickets: ReservationSchema.Dto[] = []
  get tickets() { return [...this._tickets].sort((a, b) => {
    const d1 = Temporal.PlainDateTime.from(a.date.split(" +")[0])
    const d2 = Temporal.PlainDateTime.from(b.date.split(" +")[0])
    return Temporal.PlainDate.compare(d2, d1)
  }) }
  private _isCancelTicketModalShowed = false
  get isCancelTicketModalShowed() {return this._isCancelTicketModalShowed}
  selectedTicket: ReservationSchema.Dto | null = null
  readonly showCancelTicketModal = (ticket: ReservationSchema.Dto) => {
    this._isCancelTicketModalShowed = true
    this.selectedTicket = ticket
  }
  readonly hideCancelTicketModal = () => {
    this._isCancelTicketModalShowed = false
    this.selectedTicket = null
  }
  
  async cancelTicket(id: string) {
    if (UserStore.isAuth) {
      await ReservationsApi.cancelReservation(id)
      this._tickets[this._tickets.findIndex(t => t.ticketUid === id)].status = "CANCELED"
      this.hideCancelTicketModal()
    }
  }
  
  constructor() {
    makeAutoObservable(this)
    
    // TODO dispose
    reaction(
      () => UserStore.isAuth,
      () => this.loadData()
    )
    void this.loadData()
  }
  
  async loadData() {
    if (UserStore.isAuth) {
      this._tickets = await ReservationsApi.getAllInfos()
    }
  }
}
