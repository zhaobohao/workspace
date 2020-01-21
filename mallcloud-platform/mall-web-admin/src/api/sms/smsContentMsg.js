import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/sms/smsContentMsg/list',
method: 'get',
params: params
})
}
export function createSmsContentMsg(data) {
return request({
url: '/sms/smsContentMsg/create',
method: 'post',
data: data
})
}

export function deleteSmsContentMsg(id) {
return request({
url: '/sms/smsContentMsg/delete/' + id,
method: 'get',
})
}

export function getSmsContentMsg(id) {
return request({
url: '/sms/smsContentMsg/' + id,
method: 'get',
})
}

export function updateSmsContentMsg(id, data) {
return request({
url: '/sms/smsContentMsg/update/' + id,
method: 'post',
data: data
})
}

