import request from '@/utils/request'
export function fetchList(parentId,params) {
  return request({
    url:'/api-goods/pms/PmsProductCategory/list/'+parentId,
    method:'get',
    params:params
  })
}
export function deleteProductCate(id) {
  return request({
    url:'/api-goods/pms/PmsProductCategory/delete/'+id,
    method:'get'
  })
}

export function createProductCate(data) {
  return request({
    url:'/api-goods/pms/PmsProductCategory/create',
    method:'post',
    data:data
  })
}

export function updateProductCate(id,data) {
  return request({
    url:'/api-goods/pms/PmsProductCategory/update/'+id,
    method:'post',
    data:data
  })
}

export function getProductCate(id) {
  return request({
    url:'/api-goods/pms/PmsProductCategory/'+id,
    method:'get',
  })
}

export function updateShowStatus(data) {
  return request({
    url:'/api-goods/pms/PmsProductCategory/update/showStatus',
    method:'post',
    data:data
  })
}

export function updateNavStatus(data) {
  return request({
    url:'/api-goods/pms/PmsProductCategory/update/navStatus',
    method:'post',
    data:data
  })
}

export function fetchListWithChildren() {
  return request({
    url:'/api-goods/pms/PmsProductCategory/list/withChildren',
    method:'get'
  })
}

export function updateIndexStatus(data) {
  return request({
    url:'/api-goods/pms/PmsProductCategory/update/indexStatus',
    method:'post',
    data:data
  })
}
