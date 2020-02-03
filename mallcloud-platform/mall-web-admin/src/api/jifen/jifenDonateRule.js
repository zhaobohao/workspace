import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/api-sms/jifen/jifenDonateRule/list',
    method: 'get',
    params: params
  })
}
export function createJifenDonateRule(data) {
  return request({
    url: '/api-sms/jifen/jifenDonateRule/create',
    method: 'post',
    data: data
  })
}

export function deleteJifenDonateRule(id) {
  return request({
    url: '/api-sms/jifen/jifenDonateRule/delete/' + id,
    method: 'get',
  })
}

export function getJifenDonateRule(id) {
  return request({
    url: '/api-sms/jifen/jifenDonateRule/' + id,
    method: 'get',
  })
}

export function updateJifenDonateRule(id, data) {
  return request({
    url: '/api-sms/jifen/jifenDonateRule/update/' + id,
    method: 'post',
    data: data
  })
}