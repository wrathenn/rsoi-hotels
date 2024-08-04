import { LoyaltyApi } from 'api/endpoints/loyalty.ts'
import { LoyaltySchema } from 'api/schemas/loyaltySchema.ts'
import { makeAutoObservable, reaction } from "mobx"
import { UserStore } from 'stores/user.store.ts'

export const ProfileVm = new class {
  private _privilegeInfo: LoyaltySchema.PrivilegeInfo | null = null
  get privilegeInfo(): LoyaltySchema.PrivilegeInfo | null {
    if (!this._privilegeInfo) return null
    
    return {
      balance: this._privilegeInfo.balance,
      status: this._privilegeInfo.status,
      history: [...this._privilegeInfo.history].reverse().filter(i => i.balanceDiff !== 0)
    }
  }
  
  constructor() {
    makeAutoObservable(this)
    
    // TODO dispose
    reaction(
      () => UserStore.isAuth,
      () => this.loadData()
    )
    void this.loadData()
  }
  
  async loadData() {
    if (UserStore.isAuth) {
      this._privilegeInfo = await LoyaltyApi.getLoyalty()
      console.log(this.privilegeInfo?.history.map(h => h.operationType))
    }
  }
}
