import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/sys/sysTest/list',
method: 'get',
params: params
})
}
export function createSysTest(data) {
return request({
url: '/sys/sysTest/create',
method: 'post',
data: data
})
}

export function deletes(id) {
return request({
url: '/sys/sysTest/delete/' + id,
method: 'delete',
})
}

export function getSysTest(id) {
return request({
url: '/sys/sysTest/' + id,
method: 'get',
})
}

export function updateSysTest(id, data) {
return request({
url: '/sys/sysTest/update/' + id,
method: 'post',
data: data
})
}

