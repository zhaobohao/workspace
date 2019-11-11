import request from '@/utils/request'
// import {
//   baseUrl
// } from '@/config/env'

export const loginByUsername = (tenantId, account, password, grantType) => request({
  url: '/springcloud-auth/token',
  method: 'post',
  params: {
    tenantId,
    account,
    password,
    grantType
  }
})

export const getButtons = () => request({
  url: '/springcloud-system/menu/buttons',
  method: 'get'
})

export const getUserInfo = () => request({
  url: '/springcloud-user/get-current-user-info',
  method: 'get'
})

export const refeshToken = (grantType, refreshToken) => request({
  url: '/springcloud-auth/token',
  method: 'post',
  params: {
    grantType,
    refreshToken
  }
})

export const getMenu = () => request({
  url: '/springcloud-system/menu/routes',
  method: 'get'
})

export const sendLogs = (list) => request({
  url: '/springcloud-log/saveErrorLog',
  method: 'post',
  data: list
})

export const logout = () => request({
  url: '/springcloud-user/logout',
  method: 'get'
})
