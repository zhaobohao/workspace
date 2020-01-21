import request from '@/utils/request'
export function getOrderSetting(id) {
  return request({
    url:'/api-member/ums/SysAppletSet/'+id,
    method:'get',
  })
}

export function updateOrderSetting(id,data) {
  return request({
    url:'/api-member/ums/SysAppletSet/update/'+id,
    method:'post',
    data:data
  })
}
