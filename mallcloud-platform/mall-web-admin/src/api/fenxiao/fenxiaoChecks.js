import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url: '/api-sms/fenxiao/fenxiaoChecks/list',
        method: 'get',
        params: params
    })
}
export function createFenxiaoChecks(data) {
    return request({
        url: '/api-sms/fenxiao/fenxiaoChecks/create',
        method: 'post',
        data: data
    })
}

export function deleteFenxiaoChecks(id) {
    return request({
        url: '/api-sms/fenxiao/fenxiaoChecks/delete/' + id,
        method: 'get',
    })
}

export function getFenxiaoChecks(id) {
    return request({
        url: '/api-sms/fenxiao/fenxiaoChecks/' + id,
        method: 'get',
    })
}

export function updateFenxiaoChecks(id, data) {
    return request({
        url: '/api-sms/fenxiao/fenxiaoChecks/update/' + id,
        method: 'post',
        data: data
    })
}
export function updateShowStatus(params) {
    return request({
        url: '/api-sms/fenxiao/fenxiaoChecks/update/updateShowStatus',
        method: 'post',
        params: params
    })
}