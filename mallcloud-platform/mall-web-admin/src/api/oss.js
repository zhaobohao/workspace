import request from '@/utils/request'
export function policy() {
  return request({
    url:'/api-goods/aliyun/oss/policy',
    method:'get',
  })
}
