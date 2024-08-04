import { HotelSchema } from "api/schemas/hotelSchema.ts";
import { PaymentSchema } from "api/schemas/paymentSchema.ts";

export namespace ReservationSchema {

  export type Status = "PAID" | "CANCELED";

  export type Dto = {
    reservationUid: string
    hotelUid: string
    startDate?: Temporal.PlainDate
    endDate?: Temporal.PlainDate

    discount: number

    status: Status

    payment: PaymentSchema.Dto
  }

  export type InfoDto = {
    reservationUid: string
    hotel: HotelSchema.ShortDto
    startDate?: Temporal.PlainDate
    endDate?: Temporal.PlainDate
    status: Status
    payment: PaymentSchema.Dto | {}
  }

  export type Request = {
    hotelUid: string
    startDate: Temporal.PlainDate
    endDate: Temporal.PlainDate
  }
}