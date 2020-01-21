import request from '@/utils/request'

export function fetchList(params) {
  return request({
    url: '/api-sms/marking/SmsFlashPromotionSession/list',
    method: 'get',
    params: params
  })
}

export function fetchSelectList(params) {
  return request({
    url: '/api-sms/marking/SmsFlashPromotionSession/selectList',
    method: 'get',
    params: params
  })
}

export function updateStatus(id, params) {
  return request({
    url: '/api-sms/marking/SmsFlashPromotionSession/update/status/' + id,
    method: 'post',
    params: params
  })
}

export function deleteSession(id) {
  return request({
    url: '/api-sms/marking/SmsFlashPromotionSession/delete/' + id,
    method: 'post'
  })
}

export function createSession(data) {
  return request({
    url: '/api-sms/marking/SmsFlashPromotionSession/create',
    method: 'post',
    data: data
  })
}

export function updateSession(id, data) {
  return request({
    url: '/api-sms/marking/SmsFlashPromotionSession/update/' + id,
    method: 'post',
    data: data
  })
}
