import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/fenxiao/fenxiaoRecords/list',
method: 'get',
params: params
})
}
export function createFenxiaoRecords(data) {
return request({
url: '/fenxiao/fenxiaoRecords/create',
method: 'post',
data: data
})
}

export function deleteFenxiaoRecords(id) {
return request({
url: '/fenxiao/fenxiaoRecords/delete/' + id,
method: 'get',
})
}

export function getFenxiaoRecords(id) {
return request({
url: '/fenxiao/fenxiaoRecords/' + id,
method: 'get',
})
}

export function updateFenxiaoRecords(id, data) {
return request({
url: '/fenxiao/fenxiaoRecords/update/' + id,
method: 'post',
data: data
})
}

