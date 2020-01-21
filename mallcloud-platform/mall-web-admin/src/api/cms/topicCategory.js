import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-cms/cms/CmsTopicCategory/list',
        method:'get',
        params:params
    })
}
export function createTopicCategory(data) {
    return request({
        url:'/api-cms/cms/CmsTopicCategory/create',
        method:'post',
        data:data
    })
}

export function deleteTopicCategory(id) {
    return request({
        url:'/api-cms/cms/CmsTopicCategory/delete/'+id,
        method:'get',
    })
}

export function getTopicCategory(id) {
    return request({
        url:'/api-cms/cms/CmsTopicCategory/'+id,
        method:'get',
    })
}

export function updateTopicCategory(id,data) {
    return request({
        url:'/api-cms/cms/CmsTopicCategory/update/'+id,
        method:'post',
        data:data
    })
}

