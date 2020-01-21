import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/fenxiao/fenxiaoUserRelate/list',
method: 'get',
params: params
})
}
export function createFenxiaoUserRelate(data) {
return request({
url: '/fenxiao/fenxiaoUserRelate/create',
method: 'post',
data: data
})
}

export function deleteFenxiaoUserRelate(id) {
return request({
url: '/fenxiao/fenxiaoUserRelate/delete/' + id,
method: 'get',
})
}

export function getFenxiaoUserRelate(id) {
return request({
url: '/fenxiao/fenxiaoUserRelate/' + id,
method: 'get',
})
}

export function updateFenxiaoUserRelate(id, data) {
return request({
url: '/fenxiao/fenxiaoUserRelate/update/' + id,
method: 'post',
data: data
})
}

