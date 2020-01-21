import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-cms/cms/CmsTopicComment/list',
        method:'get',
        params:params
    })
}
export function createTopicComment(data) {
    return request({
        url:'/api-cms/cms/CmsTopicComment/create',
        method:'post',
        data:data
    })
}

export function deleteTopicComment(id) {
    return request({
        url:'/api-cms/cms/CmsTopicComment/delete/'+id,
        method:'get',
    })
}

export function getTopicComment(id) {
    return request({
        url:'/api-cms/cms/CmsTopicComment/'+id,
        method:'get',
    })
}

export function updateTopicComment(id,data) {
    return request({
        url:'/api-cms/cms/CmsTopicComment/update/'+id,
        method:'post',
        data:data
    })
}

