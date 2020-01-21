import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-user/sys/SysAdminLog/list',
        method:'get',
        params:params
    })
}

export function logStatic(params) {
    return request({
        url:'/api-user/sys/SysAdminLog/logStatic',
        method:'get',
        params:params
    })
}

export function createAdminLog(data) {
    return request({
        url:'/api-user/sys/SysAdminLog/create',
        method:'post',
        data:data
    })
}

export function deleteAdminLog(id) {
    return request({
        url:'/api-user/sys/SysAdminLog/delete/'+id,
        method:'get',
    })
}

export function getAdminLog(id) {
    return request({
        url:'/api-user/sys/SysAdminLog/'+id,
        method:'get',
    })
}

export function updateAdminLog(id,data) {
    return request({
        url:'/api-user/sys/SysAdminLog/update/'+id,
        method:'post',
        data:data
    })
}

