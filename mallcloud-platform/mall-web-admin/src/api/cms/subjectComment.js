import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-cms/cms/CmsSubjectComment/list',
        method:'get',
        params:params
    })
}
export function createSubjectComment(data) {
    return request({
        url:'/api-cms/cms/CmsSubjectComment/create',
        method:'post',
        data:data
    })
}

export function deleteSubjectComment(id) {
    return request({
        url:'/api-cms/cms/CmsSubjectComment/delete/'+id,
        method:'get',
    })
}

export function getSubjectComment(id) {
    return request({
        url:'/api-cms/cms/CmsSubjectComment/'+id,
        method:'get',
    })
}

export function updateSubjectComment(id,data) {
    return request({
        url:'/api-cms/cms/CmsSubjectComment/update/'+id,
        method:'post',
        data:data
    })
}

