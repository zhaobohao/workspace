import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/api-sms/sms/smsDiypageTemplateCategory/list',
    method: 'get',
    params: params
  })
}
export function createSmsDiypageTemplateCategory(data) {
  return request({
    url: '/api-sms/sms/smsDiypageTemplateCategory/create',
    method: 'post',
    data: data
  })
}

export function deleteSmsDiypageTemplateCategory(id) {
  return request({
    url: '/api-sms/sms/smsDiypageTemplateCategory/delete/' + id,
    method: 'delete',
  })
}

export function getSmsDiypageTemplateCategory(id) {
  return request({
    url: '/api-sms/sms/smsDiypageTemplateCategory/' + id,
    method: 'get',
  })
}

export function updateSmsDiypageTemplateCategory(id, data) {
  return request({
    url: '/api-sms/sms/smsDiypageTemplateCategory/update/' + id,
    method: 'post',
    data: data
  })
}