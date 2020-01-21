import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/sys/SysStore/list',
        method:'get',
        params:params
    })
}

export function setStoreId(id) {
    return request({
        url:'/sys/SysStore/setStoreId/'+id,
        method:'get',
    })
}
export function createStore(data) {
    return request({
        url:'/sys/SysStore/create',
        method:'post',
        data:data
    })
}

export function deleteStore(id) {
    return request({
        url:'/sys/SysStore/delete/'+id,
        method:'get',
    })
}

export function getStore(id) {
    return request({
        url:'/sys/SysStore/'+id,
        method:'get',
    })
}

export function updateStore(id,data) {
    return request({
        url:'/sys/SysStore/update/'+id,
        method:'post',
        data:data
    })
}

export function listBakCate(params) {
    return request({
        url:'/sys/SysStore/listBakCate',
        method:'get',
        params:params
    })
}
