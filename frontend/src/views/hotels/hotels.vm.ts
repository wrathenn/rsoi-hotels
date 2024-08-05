import { HotelsApi } from 'api/endpoints/hotels.ts'
import { HotelSchema } from 'api/schemas/hotelSchema.ts'
import { makeAutoObservable } from "mobx"

export const HotelsVm = new class {
  hotels: HotelSchema.Dto[] = []

  private _isReserveHotelModalShowed = false
  get isReserveHotelModalShowed() {return this._isReserveHotelModalShowed}

  selectedHotel: HotelSchema.Dto | null = null
  readonly showReserveHotelModal = (hotel: HotelSchema.Dto) => {
    this._isReserveHotelModalShowed = true
    this.selectedHotel = hotel
  }
  readonly hideReserveHotelModal = () => {
    this._isReserveHotelModalShowed = false
    this.selectedHotel = null
  }
  
  constructor() {
    makeAutoObservable(this)
  }
  
  async loadData() {
    this.hotels = await HotelsApi.getHotels(1, 10)
  }
}
