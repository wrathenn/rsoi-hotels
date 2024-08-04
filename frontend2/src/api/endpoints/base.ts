import axios from 'axios'
import { BACKEND_BASE_URL } from 'config.ts'
import { getKcloakToken, keycloak } from "keycloak/kcloak.ts";

const apiClient = axios.create({
  baseURL: BACKEND_BASE_URL,
})

apiClient.interceptors.request.use( async (req) => {
  const token = await getKcloakToken()
  req.headers.Authorization = `Bearer ${token}`
  req.headers.set("X-User-Name", "test")
  return req
})

export {apiClient}
