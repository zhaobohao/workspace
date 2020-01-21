import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/sms/smsDiypageTemplateCategory/list',
method: 'get',
params: params
})
}
export function createSmsDiypageTemplateCategory(data) {
return request({
url: '/sms/smsDiypageTemplateCategory/create',
method: 'post',
data: data
})
}

export function deleteSmsDiypageTemplateCategory(id) {
return request({
url: '/sms/smsDiypageTemplateCategory/delete/' + id,
method: 'delete',
})
}

export function getSmsDiypageTemplateCategory(id) {
return request({
url: '/sms/smsDiypageTemplateCategory/' + id,
method: 'get',
})
}

export function updateSmsDiypageTemplateCategory(id, data) {
return request({
url: '/sms/smsDiypageTemplateCategory/update/' + id,
method: 'post',
data: data
})
}

