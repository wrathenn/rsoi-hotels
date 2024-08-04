import axios from 'axios'
import { BACKEND_BASE_URL } from 'config.ts'
import { getKcloakToken } from "keycloak/kcloak.ts";

const apiClient = axios.create({
  baseURL: BACKEND_BASE_URL,
})

apiClient.interceptors.request.use( req => {
  const token = getKcloakToken()
  req.headers.Authorization = `Bearer ${token}`
  return req
})

export {apiClient}
