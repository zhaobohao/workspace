import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/building/owner/list',
        method:'get',
        params:params
    })
}
export function createOwner(data) {
    return request({
        url:'/building/owner/create',
        method:'post',
        data:data
    })
}

export function deleteOwner(id) {
    return request({
        url:'/building/owner/delete/'+id,
        method:'get',
    })
}

export function getOwner(id) {
    return request({
        url:'/building/owner/'+id,
        method:'get',
    })
}

export function updateOwner(id,data) {
    return request({
        url:'/building/owner/update/'+id,
        method:'post',
        data:data
    })
}

