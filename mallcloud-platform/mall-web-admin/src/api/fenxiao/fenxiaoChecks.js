import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/fenxiao/fenxiaoChecks/list',
method: 'get',
params: params
})
}
export function createFenxiaoChecks(data) {
return request({
url: '/fenxiao/fenxiaoChecks/create',
method: 'post',
data: data
})
}

export function deleteFenxiaoChecks(id) {
return request({
url: '/fenxiao/fenxiaoChecks/delete/' + id,
method: 'get',
})
}

export function getFenxiaoChecks(id) {
return request({
url: '/fenxiao/fenxiaoChecks/' + id,
method: 'get',
})
}

export function updateFenxiaoChecks(id, data) {
return request({
url: '/fenxiao/fenxiaoChecks/update/' + id,
method: 'post',
data: data
})
}
export function updateShowStatus(params) {
    return request({
        url:'/fenxiao/fenxiaoChecks/update/updateShowStatus',
        method:'post',
        params:params
    })
}

