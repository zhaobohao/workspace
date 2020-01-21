import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-goods/marking/SmsHomeRecommendProduct/list',
    method:'get',
    params:params
  })
}

export function updateRecommendStatus(data) {
  return request({
    url:'/api-goods/marking/SmsHomeRecommendProduct/update/recommendStatus',
    method:'post',
    data:data
  })
}

export function deleteHotProduct(id) {
  return request({
    url:'/api-goods/marking/SmsHomeRecommendProduct/delete/'+id,
    method:'get'
  })
}

export function createHotProduct(data) {
  return request({
    url:'/api-goods/marking/SmsHomeRecommendProduct/create',
    method:'post',
    data:data
  })
}

export function updateHotProductSort(params) {
  return request({
    url:'/api-goods/marking/SmsHomeRecommendProduct/update/sort/'+params.id,
    method:'post',
    params:params
  })
}
