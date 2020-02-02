import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/build/wuyeCompany/list',
        method:'get',
        params:params
    })
}
export function createWuyeCompany(data) {
    return request({
        url:'/build/wuyeCompany/create',
        method:'post',
        data:data
    })
}

export function deleteWuyeCompany(id) {
    return request({
        url:'/build/wuyeCompany/delete/'+id,
        method:'get',
    })
}

export function getWuyeCompany(id) {
    return request({
        url:'/build/wuyeCompany/'+id,
        method:'get',
    })
}

export function updateWuyeCompany(id,data) {
    return request({
        url:'/build/wuyeCompany/update/'+id,
        method:'post',
        data:data
    })
}

