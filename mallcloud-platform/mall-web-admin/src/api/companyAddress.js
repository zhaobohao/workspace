import request from '@/utils/request'
export function fetchList() {
  return request({
    url:'/api-order/oms/OmsCompanyAddress/list',
    method:'get'
  })
}
