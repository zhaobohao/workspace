import request from '@/utils/request'
export function fetchList(cid,params) {
  return request({
    url:'/api-goods/pms/PmsProductAttribute/list/'+cid,
    method:'get',
    params:params
  })
}

export function deleteProductAttr(id) {
  return request({
    url:'/api-goods/pms/PmsProductAttribute/delete/'+id,
    method:'get'

  })
}

export function createProductAttr(data) {
  return request({
    url:'/api-goods/pms/PmsProductAttribute/create',
    method:'post',
    data:data
  })
}

export function updateProductAttr(id,data) {
  return request({
    url:'/api-goods/pms/PmsProductAttribute/update/'+id,
    method:'post',
    data:data
  })
}
export function getProductAttr(id) {
  return request({
    url:'/api-goods/pms/PmsProductAttribute/'+id,
    method:'get'
  })
}

export function getProductAttrInfo(productCategoryId) {
  return request({
    url:'/api-goods/pms/PmsProductAttribute/attrInfo/'+productCategoryId,
    method:'get'
  })
}

export function fetchAllList(params) {
  return request({
    url:'/api-goods/pms/PmsProductAttribute/list',
    method:'get',
    params:params
  })
}
