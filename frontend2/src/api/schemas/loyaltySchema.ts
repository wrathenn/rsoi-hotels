export namespace LoyaltySchema {
  export type Status = "BRONZE" | "GOLD" | "SILVER"
  export type Dto = {
    status: Status
    discount: number
    reservationCount: number
  }

  export type ShortDto = {
    status: Status
    discount: number
  }
}
