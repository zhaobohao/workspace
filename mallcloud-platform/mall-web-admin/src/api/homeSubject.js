import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-cms/marking/SmsHomeRecommendSubject/list',
    method:'get',
    params:params
  })
}

export function updateRecommendStatus(data) {
  return request({
    url:'/api-cms/marking/SmsHomeRecommendSubject/update/recommendStatus',
    method:'post',
    data:data
  })
}

export function deleteHomeSubject(id) {
  return request({
    url:'/api-cms/marking/SmsHomeRecommendSubject/delete/'+id,
    method:'get'
  })
}

export function createHomeSubject(data) {
  return request({
    url:'/api-cms/marking/SmsHomeRecommendSubject/create',
    method:'post',
    data:data
  })
}

export function updateHomeSubjectSort(params) {
  return request({
    url:'/api-cms/marking/SmsHomeRecommendSubject/update/sort/'+params.id,
    method:'post',
    params:params
  })
}
