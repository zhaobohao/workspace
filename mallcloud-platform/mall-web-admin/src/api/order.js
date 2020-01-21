import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-order/oms/OmsOrder/list',
    method:'get',
    params:params
  })
}

export function closeOrder(params) {
  return request({
    url:'/api-order/oms/OmsOrder/update/close',
    method:'post',
    params:params
  })
}

export function deleteOrder(params) {
  return request({
    url:'/api-order/oms/OmsOrder/delete',
    method:'post',
    params:params
  })
}

export function deliveryOrder(data) {
  return request({
    url:'/api-order/oms/OmsOrder/update/delivery',
    method:'post',
    data:data
  });
}

export function getOrderDetail(id) {
  return request({
    url:'/api-order/oms/OmsOrder/'+id,
    method:'get'
  });
}

export function updateReceiverInfo(data) {
  return request({
    url:'/api-order/oms/OmsOrder/update/receiverInfo',
    method:'post',
    data:data
  });
}

export function updateMoneyInfo(data) {
  return request({
    url:'/api-order/oms/OmsOrder/update/moneyInfo',
    method:'post',
    data:data
  });
}

export function updateOrderNote(params) {
  return request({
    url:'/api-order/oms/OmsOrder/update/note',
    method:'post',
    params:params
  })
}
