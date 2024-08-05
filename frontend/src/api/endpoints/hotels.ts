import { apiClient } from 'api/endpoints/base.ts'
import { CommonDto } from 'api/schemas/common.dto.ts'
import { HotelSchema } from 'api/schemas/hotelSchema.ts'

export namespace HotelsApi {
  export const getHotels = async (page: number, size: number) => {
    const res = (await apiClient.get("/hotels", {
      params: {page, size}
    })).data as CommonDto.Page<HotelSchema.Dto>
    
    return res.items;
  }
}
