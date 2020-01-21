import request from '@/utils/request'

export function get(id) {
  return request({
    url: '/sys/sysEmailConfig/' + id,
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: '/sys/sysEmailConfig/update',
    data,
    method: 'post'
  })
}

export function send(data) {
  return request({
    url: '/sys/sysEmailConfig/send',
    data,
    method: 'post'
  })
}
