import Cookies from 'js-cookie'
const TokenKey = 'x-access-token'
const RefreshTokenKey = 'x-access-refreshToken'
var inFifteenMinutes = new Date(new Date().getTime() + 60 * 60 * 1000)
export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token, {
    expires: inFifteenMinutes
  })
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getRefreshToken() {
  return Cookies.get(RefreshTokenKey)
}

export function setRefreshToken(refreshToken) {
  return Cookies.set(RefreshTokenKey, refreshToken, {
    expires: inFifteenMinutes
  })
}

export function removeRefreshToken() {
  return Cookies.remove(RefreshTokenKey)
}
