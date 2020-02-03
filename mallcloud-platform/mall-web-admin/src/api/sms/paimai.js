import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url: '/api-sms/sms/smsPaimai/list',
        method: 'get',
        params: params
    })
}


export function createPaimai(data) {
    return request({
        url: '/api-sms/sms/smsPaimai/create',
        method: 'post',
        data: data
    })
}

export function deletesmsPaiMai(id) {
    return request({
        url: '/api-sms/sms/smsPaimai/delete/' + id,
        method: 'get',
    })
}

export function getPaimai(id) {
    return request({
        url: '/api-sms/sms/smsPaimai/' + id,
        method: 'get',
    })
}

export function updatePaimai(id, data) {
    return request({
        url: '/api-sms/sms/smsPaimai/update/' + id,
        method: 'post',
        data: data
    })
}
export function fetchPaiMaiLog(params) {
    return request({
        url: '/api-sms/sms/smsPaimai/fetchPaiMaiLog',
        method: 'get',
        params: params
    })
}
export function updateShowStatus(params) {
    return request({
        url: '/api-sms/sms/smsPaimai/update/updateShowStatus',
        method: 'post',
        params: params
    })
}