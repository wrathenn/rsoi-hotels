export namespace StatSchema {
    export type Dto = {
        id: number,
        ts: Temporal.Instant,
        data: StatDataLoyaltyUpdate
    }

    export type StatDataType = "LOYALTY_FAILED_UPDATE" | "LOYALTY_RESTORED_UPDATE"

    export type LoyaltyReservationCountOperation = "INCREMENT" | "DECREMENT"

    export type StatDataLoyaltyUpdate = {
        type: StatDataType
        username: string
        loyaltyReservationCountOperation: LoyaltyReservationCountOperation
    }
}