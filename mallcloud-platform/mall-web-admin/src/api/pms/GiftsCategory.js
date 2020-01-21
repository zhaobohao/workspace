import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-goods/pms/PmsGiftsCategory/list',
        method:'get',
        params:params
    })
}
export function createGiftsCategory(data) {
    return request({
        url:'/api-goods/pms/PmsGiftsCategory/create',
        method:'post',
        data:data
    })
}

export function deleteGiftsCategory(id) {
    return request({
        url:'/api-goods/pms/PmsGiftsCategory/delete/'+id,
        method:'get',
    })
}

export function getGiftsCategory(id) {
    return request({
        url:'/api-goods/pms/PmsGiftsCategory/'+id,
        method:'get',
    })
}

export function updateGiftsCategory(id,data) {
    return request({
        url:'/api-goods/pms/PmsGiftsCategory/update/'+id,
        method:'post',
        data:data
    })
}

