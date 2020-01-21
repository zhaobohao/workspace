import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-cms/cms/CmsSubject/list',
        method:'get',
        params:params
    })
}
export function fetchSubjectList(params) {
    return request({
        url:'/api-cms/cms/CmsSubject/list',
        method:'get',
        params:params
    })
}

export function fetchListAll(params) {
    return request({
        url:'/api-cms/cms/CmsSubject/list',
        method:'get',
        params:params
    })
}
export function createSubject(data) {
    return request({
        url:'/api-cms/cms/CmsSubject/create',
        method:'post',
        data:data
    })
}

export function deleteSubject(id) {
    return request({
        url:'/api-cms/cms/CmsSubject/delete/'+id,
        method:'get',
    })
}

export function getSubject(id) {
    return request({
        url:'/api-cms/cms/CmsSubject/'+id,
        method:'get',
    })
}

export function updateSubject(id,data) {
    return request({
        url:'/api-cms/cms/CmsSubject/update/'+id,
        method:'post',
        data:data
    })
}

export function updateShowStatus(params) {
  return request({
    url:'/api-cms/cms/CmsSubject/update/updateShowStatus',
    method:'post',
    params:params
  })
}
export function updateRecommendStatus(params) {
  return request({
    url:'/api-cms/cms/CmsSubject/update/updateRecommendStatus',
    method:'post',
    params:params
  })
}
