import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-order/oms/OmsOrderReturnReason/list',
    method:'get',
    params:params
  })
}

export function deleteReason(id) {
  return request({
    url:'/api-order/oms/OmsOrderReturnReason/delete/'+id,
    method:'get'
  })
}

export function updateStatus(params) {
  return request({
    url:'/api-order/oms/OmsOrderReturnReason/update/status',
    method:'post',
    params:params
  })
}

export function addReason(data) {
  return request({
    url:'/api-order/oms/OmsOrderReturnReason/create',
    method:'post',
    data:data
  })
}

export function getReasonDetail(id) {
  return request({
    url:'/api-order/oms/OmsOrderReturnReason/'+id,
    method:'get'
  })
}

export function updateReason(id,data) {
  return request({
    url:'/api-order/oms/OmsOrderReturnReason/update/'+id,
    method:'post',
    data:data
  })
}
