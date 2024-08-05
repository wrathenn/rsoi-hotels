import { makeAutoObservable } from "mobx"
import { StatSchema } from "api/schemas/statSchema.ts";
import { StatsApi } from "api/endpoints/stats.ts";

export const StatsVm = new class {
  stats: StatSchema.Dto[] | null = null

  constructor() {
    makeAutoObservable(this)
    void this.loadData()
  }
  
  async loadData() {
      this.stats = await StatsApi.getStats()
  }
}
