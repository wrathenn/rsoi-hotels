import { apiClient } from 'api/endpoints/base.ts'
import { ReservationSchema } from 'api/schemas/reservationSchema.ts'

export namespace ReservationsApi {
  export const getInfo = async (reservationUid: string) => {
    const res = (await apiClient.get(`/reservations/${reservationUid}`)).data as ReservationSchema.InfoDto
    return res
  }

  export const getAllInfos = async () => {
    const res = (await apiClient.get("/reservations")).data as ReservationSchema.InfoDto[]
    return res
  }
  
  export const reserveHotel = async (form: ReservationSchema.Request) => {
    return (await apiClient.post("/reservations", form)).data as ReservationSchema.Dto
  }
  
  export const cancelReservation = async (reservationUid: string) => {
    return (await apiClient.delete(`/reservations/${reservationUid}`)).data
  }
}
