import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/jifen/jifenSignRule/list',
method: 'get',
params: params
})
}
export function createJifenSignRule(data) {
return request({
url: '/jifen/jifenSignRule/create',
method: 'post',
data: data
})
}

export function deleteJifenSignRule(id) {
return request({
url: '/jifen/jifenSignRule/delete/' + id,
method: 'get',
})
}

export function getJifenSignRule(id) {
return request({
url: '/jifen/jifenSignRule/' + id,
method: 'get',
})
}

export function updateJifenSignRule(id, data) {
return request({
url: '/jifen/jifenSignRule/update/' + id,
method: 'post',
data: data
})
}

