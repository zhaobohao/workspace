import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-sms/sms/SmsBasicMarking/list',
        method:'get',
        params:params
    })
}

export function createSmsBasicMarking(data) {
    return request({
        url:'/api-sms/sms/SmsBasicMarking/create',
        method:'post',
        data:data
    })
}

export function deleteSmsBasicMarking(id) {
    return request({
        url:'/api-sms/sms/SmsBasicMarking/delete/'+id,
        method:'get',
    })
}

export function getSmsBasicMarking(id) {
    return request({
        url:'/api-sms/sms/SmsBasicMarking/'+id,
        method:'get',
    })
}

export function updateSmsBasicMarking(id,data) {
    return request({
        url:'/api-sms/sms/SmsBasicMarking/update/'+id,
        method:'post',
        data:data
    })
}
export function updateStatus(params) {
  return request({
    url:'/api-sms/sms/SmsBasicMarking/publishStatus',
    method:'post',
    params:params
  })
}