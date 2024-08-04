import { LoyaltyApi } from 'api/endpoints/loyalty.ts'
import { LoyaltySchema } from 'api/schemas/loyaltySchema.ts'
import { makeAutoObservable } from "mobx"

export const ProfileVm = new class {
  privilegeInfo: LoyaltySchema.Dto | null = null

  constructor() {
    makeAutoObservable(this)
    void this.loadData()
  }
  
  async loadData() {
      this.privilegeInfo = await LoyaltyApi.getLoyalty()
  }
}
