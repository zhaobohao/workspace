import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-goods/pms/PmsProductAttributeCategory/list',
    method:'get',
    params:params
  })
}

export function createProductAttrCate(data) {
  return request({
    url:'/api-goods/pms/PmsProductAttributeCategory/create',
    method:'post',
    data:data
  })
}

export function deleteProductAttrCate(id) {
  return request({
    url:'/api-goods/pms/PmsProductAttributeCategory/delete/'+id,
    method:'get'
  })
}

export function updateProductAttrCate(id,data) {
  return request({
    url:'/api-goods/pms/PmsProductAttributeCategory/update/'+id,
    method:'post',
    data:data
  })
}
export function fetchListWithAttr() {
  return request({
    url:'/api-goods/pms/PmsProductAttributeCategory/list/withAttr',
    method:'get'
  })
}
