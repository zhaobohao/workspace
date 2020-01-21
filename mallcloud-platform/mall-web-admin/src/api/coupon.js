import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-sms/marking/SmsCoupon/list',
    method:'get',
    params:params
  })
}

export function createCoupon(data) {
  return request({
    url:'/api-sms/marking/SmsCoupon/create',
    method:'post',
    data:data
  })
}

export function getCoupon(id) {
  return request({
    url:'/api-sms/marking/SmsCoupon/'+id,
    method:'get',
  })
}

export function updateCoupon(id,data) {
  return request({
    url:'/api-sms/marking/SmsCoupon/update/'+id,
    method:'post',
    data:data
  })
}

export function deleteCoupon(id) {
  return request({
    url:'/api-sms/marking/SmsCoupon/delete/'+id,
    method:'post',
  })
}
