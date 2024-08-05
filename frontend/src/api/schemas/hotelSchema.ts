export namespace HotelSchema {
  export type Dto = {
    hotelUid: string
    name: string
    country: string
    city: string
    address: string
    stars?: number
    price: number
  }

  export type ShortDto = {
    hotelUid: string
    name: string
    fullAddress: string
    stars?: number
  }
}