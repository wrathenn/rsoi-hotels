import { apiClient } from 'api/endpoints/base.ts'
import { LoyaltySchema } from 'api/schemas/loyaltySchema.ts'

export namespace LoyaltyApi {
  export const getLoyalty = async () => {
    return (await apiClient.get("/loyalty")).data as LoyaltySchema.Dto
  }
}
