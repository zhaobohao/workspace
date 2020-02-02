import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/building/room/list',
        method:'get',
        params:params
    })
}
export function withChilds(params) {
    return request({
        url:'/building/room/withChilds',
        method:'get',
        params:params
    })
}
export function createRoom(data) {
    return request({
        url:'/building/room/create',
        method:'post',
        data:data
    })
}

export function deleteRoom(id) {
    return request({
        url:'/building/room/delete/'+id,
        method:'get',
    })
}

export function getRoom(id) {
    return request({
        url:'/building/room/'+id,
        method:'get',
    })
}

export function updateRoom(id,data) {
    return request({
        url:'/building/room/update/'+id,
        method:'post',
        data:data
    })
}

