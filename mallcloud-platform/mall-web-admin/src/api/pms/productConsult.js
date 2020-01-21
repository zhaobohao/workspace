import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-goods/pms/PmsProductConsult/list',
        method:'get',
        params:params
    })
}
export function createGifts(data) {
    return request({
        url:'/api-goods/pms/PmsProductConsult/create',
        method:'post',
        data:data
    })
}

export function deleteGifts(id) {
    return request({
        url:'/api-goods/pms/PmsProductConsult/delete/'+id,
        method:'get',
    })
}

export function getGifts(id) {
    return request({
        url:'/api-goods/pms/PmsProductConsult/'+id,
        method:'get',
    })
}

export function updateGifts(id,data) {
    return request({
        url:'/api-goods/pms/PmsProductConsult/update/'+id,
        method:'post',
        data:data
    })
}

export function updateShowStatus(params) {
  return request({
    url:'/api-goods/pms/PmsProductConsult/update/updateShowStatus',
    method:'post',
    params:params
  })
}
