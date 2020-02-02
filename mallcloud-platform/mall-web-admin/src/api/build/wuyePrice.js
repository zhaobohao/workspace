import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/build/wuyePrice/list',
        method:'get',
        params:params
    })
}
export function createWuyePrice(data) {
    return request({
        url:'/build/wuyePrice/create',
        method:'post',
        data:data
    })
}

export function deleteWuyePrice(id) {
    return request({
        url:'/build/wuyePrice/delete/'+id,
        method:'get',
    })
}

export function getWuyePrice(id) {
    return request({
        url:'/build/wuyePrice/'+id,
        method:'get',
    })
}

export function updateWuyePrice(id,data) {
    return request({
        url:'/build/wuyePrice/update/'+id,
        method:'post',
        data:data
    })
}

