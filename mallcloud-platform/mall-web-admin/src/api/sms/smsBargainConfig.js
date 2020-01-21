import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/sms/smsBargainConfig/list',
method: 'get',
params: params
})
}
export function createSmsBargainConfig(data) {
return request({
url: '/sms/smsBargainConfig/create',
method: 'post',
data: data
})
}

export function deleteSmsBargainConfig(id) {
return request({
url: '/sms/smsBargainConfig/delete/' + id,
method: 'get',
})
}

export function getSmsBargainConfig(id) {
return request({
url: '/sms/smsBargainConfig/' + id,
method: 'get',
})
}

export function updateSmsBargainConfig(id, data) {
return request({
url: '/sms/smsBargainConfig/update/' + id,
method: 'post',
data: data
})
}

