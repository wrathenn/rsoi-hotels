import { apiClient } from 'api/endpoints/base.ts'
import { StatSchema } from "api/schemas/statSchema.ts";

export namespace StatsApi {
  export const getStats = async () => {
    return (await apiClient.get("/stats")).data as StatSchema.Dto[]
  }
}
