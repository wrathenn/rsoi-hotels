
export namespace PaymentSchema {
    export type Status = "PAID" | "CANCELED";

    export type Dto = {
        status: Status,
        price: number,
    }
}
