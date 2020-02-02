import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/build/group/list',
        method:'get',
        params:params
    })
}
export function createGroup(data) {
    return request({
        url:'/build/group/create',
        method:'post',
        data:data
    })
}

export function deleteGroup(id) {
    return request({
        url:'/build/group/delete/'+id,
        method:'get',
    })
}

export function getGroup(id) {
    return request({
        url:'/build/group/'+id,
        method:'get',
    })
}

export function updateGroup(id,data) {
    return request({
        url:'/build/group/update/'+id,
        method:'post',
        data:data
    })
}

