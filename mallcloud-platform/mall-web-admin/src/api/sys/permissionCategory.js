import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/sys/permissionCategory/list',
        method:'get',
        params:params
    })
}
export function createPermissionCategory(data) {
    return request({
        url:'/sys/permissionCategory/create',
        method:'post',
        data:data
    })
}

export function deletePermissionCategory(id) {
    return request({
        url:'/sys/permissionCategory/delete/'+id,
        method:'get',
    })
}

export function getPermissionCategory(id) {
    return request({
        url:'/sys/permissionCategory/'+id,
        method:'get',
    })
}

export function updatePermissionCategory(id,data) {
    return request({
        url:'/sys/permissionCategory/update/'+id,
        method:'post',
        data:data
    })
}

