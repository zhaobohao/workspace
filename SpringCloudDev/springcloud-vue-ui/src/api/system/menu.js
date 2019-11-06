import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/api/springcloud-system/menu/list',
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}
export const remove = (ids) => {
  return request({
    url: '/api/springcloud-system/menu/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/springcloud-system/menu/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/springcloud-system/menu/submit',
    method: 'post',
    data: row
  })
}

export const getMenu = (id) => {
  return request({
    url: '/api/springcloud-system/menu/detail',
    method: 'get',
    params: {
      id
    }
  })
}
