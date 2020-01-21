import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-sms/marking/SmsFlashPromotionProductRelation/list',
    method:'get',
    params:params
  })
}
export function createFlashProductRelation(data) {
  return request({
    url:'/api-sms/marking/SmsFlashPromotionProductRelation/create',
    method:'post',
    data:data
  })
}
export function deleteFlashProductRelation(id) {
  return request({
    url:'/api-sms/marking/SmsFlashPromotionProductRelation/delete/'+id,
    method:'post'
  })
}
export function updateFlashProductRelation(id,data) {
  return request({
    url:'/api-sms/marking/SmsFlashPromotionProductRelation/update/'+id,
    method:'post',
    data:data
  })
}

export function createFlashProductRelations(data) {
  return request({
    url:'/api-sms/sms/SmsFlashPromotionProductRelation/batchCreate',
    method:'post',
    data:data
  })
}
