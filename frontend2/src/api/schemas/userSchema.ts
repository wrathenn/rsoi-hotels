import { ReservationSchema } from "api/schemas/reservationSchema.ts";
import { LoyaltySchema } from "api/schemas/loyaltySchema.ts";

export namespace UserSchema {
    export type Dto = {
        reservations: ReservationSchema.InfoDto[]
        loyalty: LoyaltySchema.ShortDto | {}
    }
}
