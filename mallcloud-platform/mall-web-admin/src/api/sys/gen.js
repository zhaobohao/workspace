import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/gen/tables',
    method:'get',
    params:params
  })
}

export function generator(data, tableName) {
  return request({
    url: '/gen?tableName=' + tableName,
    data,
    method: 'post'
  })
}

export function get() {
  return request({
    url: 'gen/get',
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: 'gen/update',
    data,
    method: 'post'
  })
}
