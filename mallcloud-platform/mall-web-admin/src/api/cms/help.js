import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-cms/cms/CmsHelp/list',
        method:'get',
        params:params
    })
}
export function createHelp(data) {
    return request({
        url:'/api-cms/cms/CmsHelp/create',
        method:'post',
        data:data
    })
}

export function deleteHelp(id) {
    return request({
        url:'/api-cms/cms/CmsHelp/delete/'+id,
        method:'get',
    })
}

export function getHelp(id) {
    return request({
        url:'/api-cms/cms/CmsHelp/'+id,
        method:'get',
    })
}

export function updateHelp(id,data) {
    return request({
        url:'/api-cms/cms/CmsHelp/update/'+id,
        method:'post',
        data:data
    })
}

export function updateShowStatus(params) {
  return request({
    url:'/api-cms/cms/CmsHelp/update/updateShowStatus',
    method:'post',
    params:params
  })
}
