import axios from "axios"

const API_URL = "http://localhost:8088"
const CLIENT_API_URL = "http://localhost:9080/api/client/v0"
const LICENSE_API_URL = "http://localhost:9080/api/license/v0"

export const api = axios.create({
  baseURL: API_URL,
})

export const clientApi = axios.create({
  baseURL: CLIENT_API_URL,
})

export const licenseApi = axios.create({
  baseURL: LICENSE_API_URL,
})

export const setAuthToken = (token: string) => {
  api.defaults.headers.common["Authorization"] = `Bearer ${token}`
  clientApi.defaults.headers.common["Authorization"] = `Bearer ${token}`
  licenseApi.defaults.headers.common["Authorization"] = `Bearer ${token}`
}

export const clearAuthToken = () => {
  delete api.defaults.headers.common["Authorization"]
  delete clientApi.defaults.headers.common["Authorization"]
  delete licenseApi.defaults.headers.common["Authorization"]
}

