import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-member/sys/SysArea/list',
        method:'get',
        params:params
    })
}
export function createArea(data) {
    return request({
        url:'/api-member/sys/SysArea/create',
        method:'post',
        data:data
    })
}

export function deleteArea(id) {
    return request({
        url:'/api-member/sys/SysArea/delete/'+id,
        method:'get',
    })
}

export function getArea(id) {
    return request({
        url:'/api-member/sys/SysArea/'+id,
        method:'get',
    })
}

export function updateArea(id,data) {
    return request({
        url:'/api-member/sys/SysArea/update/'+id,
        method:'post',
        data:data
    })
}

export function fetchAreaListWithChildren(params) {
    return request({
        url:'/api-member/sys/SysArea/list/withChildren',
        method:'get',
        params:params
    })
}
