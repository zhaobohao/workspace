import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoRecords/list',
    method: 'get',
    params: params
  })
}
export function createFenxiaoRecords(data) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoRecords/create',
    method: 'post',
    data: data
  })
}

export function deleteFenxiaoRecords(id) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoRecords/delete/' + id,
    method: 'get',
  })
}

export function getFenxiaoRecords(id) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoRecords/' + id,
    method: 'get',
  })
}

export function updateFenxiaoRecords(id, data) {
  return request({
    url: '/api-sms/fenxiao/fenxiaoRecords/update/' + id,
    method: 'post',
    data: data
  })
}