import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/build/repair/list',
        method:'get',
        params:params
    })
}
export function createRepair(data) {
    return request({
        url:'/build/repair/create',
        method:'post',
        data:data
    })
}

export function deleteRepair(id) {
    return request({
        url:'/build/repair/delete/'+id,
        method:'get',
    })
}

export function getRepair(id) {
    return request({
        url:'/build/repair/'+id,
        method:'get',
    })
}

export function updateRepair(id,data) {
    return request({
        url:'/build/repair/update/'+id,
        method:'post',
        data:data
    })
}

