import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/sys/SysWebLog/list',
        method:'get',
        params:params
    })
}
export function createWebLog(data) {
    return request({
        url:'/sys/SysWebLog/create',
        method:'post',
        data:data
    })
}

export function deleteWebLog(id) {
    return request({
        url:'/sys/SysWebLog/delete/'+id,
        method:'get',
    })
}

export function getWebLog(id) {
    return request({
        url:'/sys/SysWebLog/'+id,
        method:'get',
    })
}

export function updateWebLog(id,data) {
    return request({
        url:'/sys/SysWebLog/update/'+id,
        method:'post',
        data:data
    })
}
export function logStatic(params) {
    return request({
        url:'/sys/SysWebLog/logStatic',
        method:'get',
        params:params
    })
}

