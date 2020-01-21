import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/toupiao/toupiaoProject/list',
method: 'get',
params: params
})
}
export function createToupiaoProject(data) {
return request({
url: '/toupiao/toupiaoProject/create',
method: 'post',
data: data
})
}

export function deleteToupiaoProject(id) {
return request({
url: '/toupiao/toupiaoProject/delete/' + id,
method: 'get',
})
}

export function getToupiaoProject(id) {
return request({
url: '/toupiao/toupiaoProject/' + id,
method: 'get',
})
}

export function updateToupiaoProject(id, data) {
return request({
url: '/toupiao/toupiaoProject/update/' + id,
method: 'post',
data: data
})
}

