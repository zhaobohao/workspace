import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url: '/sys/adminSysJob/list',
        method: 'get',
        params: params
    })
}
export function fetchJobLogList(params) {
    return request({
        url: '/sys/adminSysJobLog/list',
        method: 'get',
        params: params
    })
}
export function createJob(data) {
    return request({
        url: '/sys/adminSysJob/create',
        method: 'post',
        data: data
    })
}

export function deleteJob(id) {
    return request({
        url: '/sys/adminSysJob/delete/' + id,
        method: 'delete',
    })
}

export function getJob(id) {
    return request({
        url: '/sys/adminSysJob/' + id,
        method: 'get',
    })
}
export function updateJob(id, data) {
    return request({
        url: '/sys/adminSysJob/update/' + id,
        method: 'post',
        data: data
    })
}



export function changeStatus( data) {
    return request({
        url: '/sys/adminSysJob/changeStatus',
        method: 'post',
        data: data
    })
}

export function run( data) {
    return request({
        url: '/sys/adminSysJob/run',
        method: 'post',
        data: data
    })
}

export function checkCronExpressionIsValid( data) {
    return request({
        url: '/sys/adminSysJob/checkCronExpressionIsValid',
        method: 'post',
        data: data
    })
}
