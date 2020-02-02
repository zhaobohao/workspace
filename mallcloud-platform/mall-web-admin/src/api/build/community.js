import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/building/community/list',
        method:'get',
        params:params
    })
}
export function createCommunity(data) {
    return request({
        url:'/building/community/create',
        method:'post',
        data:data
    })
}

export function deleteCommunity(id) {
    return request({
        url:'/building/community/delete/'+id,
        method:'get',
    })
}

export function getCommunity(id) {
    return request({
        url:'/building/community/'+id,
        method:'get',
    })
}

export function updateCommunity(id,data) {
    return request({
        url:'/building/community/update/'+id,
        method:'post',
        data:data
    })
}

export function exportExcel(params) {
    window.open(process.env.BASE_API+'/building/community/exportExcel')
}
