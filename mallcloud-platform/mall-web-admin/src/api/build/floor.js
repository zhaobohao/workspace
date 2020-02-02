import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/building/floor/list',
        method:'get',
        params:params
    })
}
export function createFloor(data) {
    return request({
        url:'/building/floor/create',
        method:'post',
        data:data
    })
}

export function deleteFloor(id) {
    return request({
        url:'/building/floor/delete/'+id,
        method:'get',
    })
}

export function getFloor(id) {
    return request({
        url:'/building/floor/'+id,
        method:'get',
    })
}

export function updateFloor(id,data) {
    return request({
        url:'/building/floor/update/'+id,
        method:'post',
        data:data
    })
}

