import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url: '/api-sms/sms/smsDiyPage/list',
        method: 'get',
        params: params
    })
}
export function createSmsDiyPage(data) {
    return request({
        url: '/api-sms/sms/smsDiyPage/create',
        method: 'post',
        data: data
    })
}

export function deleteSmsDiyPage(id) {
    return request({
        url: '/api-sms/sms/smsDiyPage/delete/' + id,
        method: 'get',
    })
}

export function getSmsDiyPage(id) {
    return request({
        url: '/api-sms/sms/smsDiyPage/' + id,
        method: 'get',
    })
}

export function updateSmsDiyPage(id, data) {
    return request({
        url: '/api-sms/sms/smsDiyPage/update/' + id,
        method: 'post',
        data: data
    })
}

export function updateShowStatus(params) {
    return request({
        url: '/api-sms/sms/smsDiyPage/update/updateShowStatus',
        method: 'post',
        params: params
    })
}