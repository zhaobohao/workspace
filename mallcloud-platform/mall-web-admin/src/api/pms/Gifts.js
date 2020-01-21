import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-goods/pms/PmsGifts/list',
        method:'get',
        params:params
    })
}
export function createGifts(data) {
    return request({
        url:'/api-goods/pms/PmsGifts/create',
        method:'post',
        data:data
    })
}

export function deleteGifts(id) {
    return request({
        url:'/api-goods/pms/PmsGifts/delete/'+id,
        method:'get',
    })
}

export function getGifts(id) {
    return request({
        url:'/api-goods/pms/PmsGifts/'+id,
        method:'get',
    })
}

export function updateGifts(id,data) {
    return request({
        url:'/api-goods/pms/PmsGifts/update/'+id,
        method:'post',
        data:data
    })
}

export function updateShowStatus(params) {
  return request({
    url:'/api-goods/pms/PmsGifts/update/updateShowStatus',
    method:'post',
    params:params
  })
}
