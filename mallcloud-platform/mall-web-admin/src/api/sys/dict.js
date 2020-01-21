import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url: '/sys/SysDict/list',
        method: 'get',
        params: params
    })
}
export function createDict(data) {
    return request({
        url: '/sys/SysDict/create',
        method: 'post',
        data: data
    })
}

export function deleteDict(id) {
    return request({
        url: '/sys/SysDict/delete/' + id,
        method: 'delete',
    })
}

export function getDict(id) {
    return request({
        url: '/sys/SysDict/' + id,
        method: 'get',
    })
}

export function updateDict(id, data) {
    return request({
        url: '/sys/SysDict/update/' + id,
        method: 'post',
        data: data
    })
}

