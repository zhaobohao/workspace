import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/jifen/jifenCoupon/list',
method: 'get',
params: params
})
}
export function createJifenCoupon(data) {
return request({
url: '/jifen/jifenCoupon/create',
method: 'post',
data: data
})
}

export function deleteJifenCoupon(id) {
return request({
url: '/jifen/jifenCoupon/delete/' + id,
method: 'get',
})
}

export function getJifenCoupon(id) {
return request({
url: '/jifen/jifenCoupon/' + id,
method: 'get',
})
}

export function updateJifenCoupon(id, data) {
return request({
url: '/jifen/jifenCoupon/update/' + id,
method: 'post',
data: data
})
}

