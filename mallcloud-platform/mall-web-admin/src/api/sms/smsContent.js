import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/api-sms/sms/smsContent/list',
    method: 'get',
    params: params
  })
}
export function createSmsContent(data) {
  return request({
    url: '/api-sms/sms/smsContent/create',
    method: 'post',
    data: data
  })
}

export function deleteSmsContent(id) {
  return request({
    url: '/api-sms/sms/smsContent/delete/' + id,
    method: 'get',
  })
}

export function getSmsContent(id) {
  return request({
    url: '/api-sms/sms/smsContent/' + id,
    method: 'get',
  })
}

export function updateSmsContent(id, data) {
  return request({
    url: '/api-sms/sms/smsContent/update/' + id,
    method: 'post',
    data: data
  })
}