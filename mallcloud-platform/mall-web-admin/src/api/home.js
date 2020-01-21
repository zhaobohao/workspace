import request from '@/utils/request'
export function orderStatic() {
  return request({
    url:'/api-order/oms/OmsOrder/orderStatic',
    method:'get'
  })
}

export function goodsStatic() {
  return request({
    url:'api-goods/pms/PmsProduct/goodsStatic',
    method:'get'
  })
}

export function userStatic() {
  return request({
    url:'/api-member/ums/UmsMember/userStatic',
    method:'get'
  })
}


export function acceptRedPacket(id) {
  return request({
    url:'/api-sms/marking/SmsRedPacket/accept/'+id,
    method:'get',
  })
}
export function list() {
  return request({
    url:'/api-sms/marking/SmsRedPacket/list/',
    method:'get',
  })
}
