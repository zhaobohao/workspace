import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-cms/cms/CmsTopic/list',
        method:'get',
        params:params
    })
}
export function createTopic(data) {
    return request({
        url:'/api-cms/cms/CmsTopic/create',
        method:'post',
        data:data
    })
}

export function deleteTopic(id) {
    return request({
        url:'/api-cms/cms/CmsTopic/delete/'+id,
        method:'get',
    })
}

export function getTopic(id) {
    return request({
        url:'/api-cms/cms/CmsTopic/'+id,
        method:'get',
    })
}

export function updateTopic(id,data) {
    return request({
        url:'/api-cms/cms/CmsTopic/update/'+id,
        method:'post',
        data:data
    })
}
export function updateVerifyStatus(params) {
  return request({
    url:'/api-cms/cms/CmsTopic/update/verifyStatus',
    method:'post',
    params:params
  })
}
export function fetchCmsTopicMember(id) {
  return request({
    url:'/api-cms/cms/CmsTopic/fetchCmsTopicMember/'+id,
    method:'get',
  })
}
