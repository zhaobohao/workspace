import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/build/notice/list',
        method:'get',
        params:params
    })
}
export function createNotice(data) {
    return request({
        url:'/build/notice/create',
        method:'post',
        data:data
    })
}

export function deleteNotice(id) {
    return request({
        url:'/build/notice/delete/'+id,
        method:'get',
    })
}

export function getNotice(id) {
    return request({
        url:'/build/notice/'+id,
        method:'get',
    })
}

export function updateNotice(id,data) {
    return request({
        url:'/build/notice/update/'+id,
        method:'post',
        data:data
    })
}

