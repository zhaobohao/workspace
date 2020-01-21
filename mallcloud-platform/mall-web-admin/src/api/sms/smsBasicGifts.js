import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-sms/sms/SmsBasicGifts/list',
        method:'get',
        params:params
    })
}

export function createSmsBasicGifts(data) {
    return request({
        url:'/api-sms/sms/SmsBasicGifts/create',
        method:'post',
        data:data
    })
}

export function deleteSmsBasicGifts(id) {
    return request({
        url:'/api-sms/sms/SmsBasicGifts/delete/'+id,
        method:'get',
    })
}

export function getSmsBasicGifts(id) {
    return request({
        url:'/api-sms/sms/SmsBasicGifts/'+id,
        method:'get',
    })
}

export function updateSmsBasicGifts(id,data) {
    return request({
        url:'/api-sms/sms/SmsBasicGifts/update/'+id,
        method:'post',
        data:data
    })
}
export function updateStatus(params) {
  return request({
    url:'/api-sms/sms/SmsBasicGifts/publishStatus',
    method:'post',
    params:params
  })
}