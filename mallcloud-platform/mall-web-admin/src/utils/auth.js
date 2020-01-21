import Cookies from 'js-cookie'

const TokenKey = 'loginToken'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function get(key) {
  return Cookies.get(key)
}

export function set(key,value) {
  return Cookies.set(key, value)
}

export function remove(key) {
  return Cookies.remove(key)
}
