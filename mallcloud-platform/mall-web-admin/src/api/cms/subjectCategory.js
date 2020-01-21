import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-cms/cms/CmsSubjectCategory/list',
        method:'get',
        params:params
    })
}
export function createSubjectCategory(data) {
    return request({
        url:'/api-cms/cms/CmsSubjectCategory/create',
        method:'post',
        data:data
    })
}

export function deleteSubjectCategory(id) {
    return request({
        url:'/api-cms/cms/CmsSubjectCategory/delete/'+id,
        method:'get',
    })
}

export function getSubjectCategory(id) {
    return request({
        url:'/api-cms/cms/CmsSubjectCategory/'+id,
        method:'get',
    })
}

export function updateSubjectCategory(id,data) {
    return request({
        url:'/api-cms/cms/CmsSubjectCategory/update/'+id,
        method:'post',
        data:data
    })
}

