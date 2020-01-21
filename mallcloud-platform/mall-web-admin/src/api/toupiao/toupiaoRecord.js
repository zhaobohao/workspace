import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/toupiao/toupiaoRecord/list',
method: 'get',
params: params
})
}
export function createToupiaoRecord(data) {
return request({
url: '/toupiao/toupiaoRecord/create',
method: 'post',
data: data
})
}

export function deleteToupiaoRecord(id) {
return request({
url: '/toupiao/toupiaoRecord/delete/' + id,
method: 'get',
})
}

export function getToupiaoRecord(id) {
return request({
url: '/toupiao/toupiaoRecord/' + id,
method: 'get',
})
}

export function updateToupiaoRecord(id, data) {
return request({
url: '/toupiao/toupiaoRecord/update/' + id,
method: 'post',
data: data
})
}

