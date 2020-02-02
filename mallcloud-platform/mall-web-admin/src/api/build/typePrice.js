import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/build/typePrice/list',
        method:'get',
        params:params
    })
}
export function createTypePrice(data) {
    return request({
        url:'/build/typePrice/create',
        method:'post',
        data:data
    })
}

export function deleteTypePrice(id) {
    return request({
        url:'/build/typePrice/delete/'+id,
        method:'get',
    })
}

export function getTypePrice(id) {
    return request({
        url:'/build/typePrice/'+id,
        method:'get',
    })
}

export function updateTypePrice(id,data) {
    return request({
        url:'/build/typePrice/update/'+id,
        method:'post',
        data:data
    })
}

