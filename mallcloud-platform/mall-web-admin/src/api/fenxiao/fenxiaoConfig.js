import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoConfig/list',
    method: 'get',
    params: params
  })
}
export function createFenxiaoConfig(data) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoConfig/create',
    method: 'post',
    data: data
  })
}

export function deleteFenxiaoConfig(id) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoConfig/delete/' + id,
    method: 'get',
  })
}

export function getFenxiaoConfig(id) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoConfig/' + id,
    method: 'get',
  })
}

export function updateFenxiaoConfig(id, data) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoConfig/update/' + id,
    method: 'post',
    data: data
  })
}