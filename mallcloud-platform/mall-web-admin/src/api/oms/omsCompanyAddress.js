import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/api-order/oms/OmsCompanyAddress/list',
method: 'get',
params: params
})
}
export function createOmsCompanyAddress(data) {
return request({
url: '/api-order/oms/OmsCompanyAddress/create',
method: 'post',
data: data
})
}

export function deleteOmsCompanyAddress(id) {
return request({
url: '/api-order/oms/OmsCompanyAddress/delete/' + id,
method: 'get',
})
}

export function getOmsCompanyAddress(id) {
return request({
url: '/api-order/oms/OmsCompanyAddress/' + id,
method: 'get',
})
}

export function updateOmsCompanyAddress(id, data) {
return request({
url: '/api-order/oms/OmsCompanyAddress/update/' + id,
method: 'post',
data: data
})
}

