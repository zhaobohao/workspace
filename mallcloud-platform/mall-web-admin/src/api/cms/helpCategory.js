import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-cms/cms/CmsHelpCategory/list',
        method:'get',
        params:params
    })
}
export function createHelpCategory(data) {
    return request({
        url:'/api-cms/cms/CmsHelpCategory/create',
        method:'post',
        data:data
    })
}

export function deleteHelpCategory(id) {
    return request({
        url:'/api-cms/cms/CmsHelpCategory/delete/'+id,
        method:'get',
    })
}

export function getHelpCategory(id) {
    return request({
        url:'/api-cms/cms/CmsHelpCategory/'+id,
        method:'get',
    })
}

export function updateHelpCategory(id,data) {
    return request({
        url:'/api-cms/cms/CmsHelpCategory/update/'+id,
        method:'post',
        data:data
    })
}

