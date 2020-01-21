import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-sms/marking/SmsGroup/list',
        method:'get',
        params:params
    })
}

export function listGroupMember(params) {
  return request({
    url:'/api-sms/marking/SmsGroup/listGroupMember',
    method:'get',
    params:params
  })
}
export function createGroup(data) {
    return request({
        url:'/api-sms/marking/SmsGroup/create',
        method:'post',
        data:data
    })
}

export function deleteGroup(id) {
    return request({
        url:'/api-sms/marking/SmsGroup/delete/'+id,
        method:'get',
    })
}

export function getGroup(id) {
    return request({
        url:'/api-sms/marking/SmsGroup/'+id,
        method:'get',
    })
}

export function updateGroup(id,data) {
    return request({
        url:'/api-sms/marking/SmsGroup/update/'+id,
        method:'post',
        data:data
    })
}

