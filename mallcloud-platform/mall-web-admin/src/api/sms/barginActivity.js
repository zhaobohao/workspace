import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url: '/api-sms/sms/smsConfigure/list',
        method: 'get',
        params: params
    })
}
export function createHelpCategory(data) {
    return request({
        url: '/api-sms/sms/smsConfigure/create',
        method: 'post',
        data: data
    })
}

export function deleteHelpCategory(id) {
    return request({
        url: '/api-sms/sms/smsConfigure/delete/' + id,
        method: 'get',
    })
}

export function getHelpCategory(id) {
    return request({
        url: '/api-sms/sms/smsConfigure/' + id,
        method: 'get',
    })
}

export function updateHelpCategory(id, data) {
    return request({
        url: '/api-sms/sms/smsConfigure/update/' + id,
        method: 'post',
        data: data
    })
}