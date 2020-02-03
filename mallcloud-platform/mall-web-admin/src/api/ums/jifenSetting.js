import request from '@/utils/request'
export function getOrderSetting(id) {
  return request({
    url: '/api-member/ums/UmsIntegrationConsumeSetting/' + id,
    method: 'get',
  })
}

export function updateOrderSetting(id, data) {
  return request({
    url: '/api-member/ums/UmsIntegrationConsumeSetting/update/' + id,
    method: 'post',
    data: data
  })
}