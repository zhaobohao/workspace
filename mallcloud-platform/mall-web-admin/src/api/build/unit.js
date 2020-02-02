import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/building/unit/list',
        method:'get',
        params:params
    })
}
export function createUnit(data) {
    return request({
        url:'/building/unit/create',
        method:'post',
        data:data
    })
}

export function deleteUnit(id) {
    return request({
        url:'/building/unit/delete/'+id,
        method:'get',
    })
}

export function getUnit(id) {
    return request({
        url:'/building/unit/'+id,
        method:'get',
    })
}

export function updateUnit(id,data) {
    return request({
        url:'/building/unit/update/'+id,
        method:'post',
        data:data
    })
}

