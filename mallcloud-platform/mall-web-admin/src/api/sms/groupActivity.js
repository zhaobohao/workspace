import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/sms/smsGroupActivity/list',
        method:'get',
        params:params
    })
}
export function createHelpCategory(data) {
    return request({
        url:'/sms/smsGroupActivity/create',
        method:'post',
        data:data
    })
}

export function deleteHelpCategory(id) {
    return request({
        url:'/sms/smsGroupActivity/delete/'+id,
        method:'get',
    })
}

export function getHelpCategory(id) {
    return request({
        url:'/sms/smsGroupActivity/'+id,
        method:'get',
    })
}

export function updateHelpCategory(id,data) {
    return request({
        url:'/sms/smsGroupActivity/update/'+id,
        method:'post',
        data:data
    })
}

