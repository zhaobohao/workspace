import request from '@/utils/request'


// 支付
export function toAliPay(url, data) {
  return request({
    url: '/api/' + url,
    data,
    method: 'post'
  })
}

export function get(id) {
  return request({
    url: '/api/aliPay/' + id,
    method: 'get',
  })
}

export function update( data) {
  return request({
    url: '/api/aliPay/update',
    method: 'post',
    data: data
  })
}
