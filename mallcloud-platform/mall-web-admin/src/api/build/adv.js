import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/build/adv/list',
        method:'get',
        params:params
    })
}
export function createAdv(data) {
    return request({
        url:'/build/adv/create',
        method:'post',
        data:data
    })
}

export function deleteAdv(id) {
    return request({
        url:'/build/adv/delete/'+id,
        method:'get',
    })
}

export function getAdv(id) {
    return request({
        url:'/build/adv/'+id,
        method:'get',
    })
}

export function updateAdv(id,data) {
    return request({
        url:'/build/adv/update/'+id,
        method:'post',
        data:data
    })
}
export function updateStatus(id,params) {
    return request({
        url:'/build/adv/update/status/'+id,
        method:'post',
        params:params
    })
}
