import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-goods/pms/PmsProduct/list',
    method:'get',
    params:params
  })
}

export function fetchSimpleList(params) {
  return request({
    url:'/api-goods/pms/PmsProduct/simpleList',
    method:'get',
    params:params
  })
}

export function updateDeleteStatus(params) {
  return request({
    url:'/api-goods/pms/PmsProduct/update/deleteStatus',
    method:'post',
    params:params
  })
}
export function deleteProduct(id) {
  return request({
    url:'/api-goods/pms/PmsProduct/delete/'+id,
    method:'get',
  })
}

export function updateNewStatus(params) {
  return request({
    url:'/api-goods/pms/PmsProduct/update/newStatus',
    method:'post',
    params:params
  })
}

export function updateRecommendStatus(params) {
  return request({
    url:'/api-goods/pms/PmsProduct/update/recommendStatus',
    method:'post',
    params:params
  })
}

export function updateVerifyStatus(params) {
  return request({
    url:'/api-goods/pms/PmsProduct/update/verifyStatus',
    method:'post',
    params:params
  })
}

export function updatePublishStatus(params) {
  return request({
    url:'/api-goods/pms/PmsProduct/update/publishStatus',
    method:'post',
    params:params
  })
}
export function createProduct(data) {
  return request({
    url:'/api-goods/pms/PmsProduct/create',
    method:'post',
    data:data
  })
}

export function updateProduct(id,data) {
  return request({
    url:'/api-goods/pms/PmsProduct/update/'+id,
    method:'post',
    data:data
  })
}

export function fetchVList(id) {
  return request({
    url:'/api-goods/pms/PmsProduct/fetchVList/'+id,
    method:'get',
  })
}
export function getProduct(id) {
  return request({
    url:'/api-goods/pms/PmsProduct/updateInfo/'+id,
    method:'get',
  })
}

