import request from '@/utils/request'
export function fetchList() {
  return request({
    url:'/api-cms/cms/CmsPrefrenceArea/list',
    method:'get',
  })
}
