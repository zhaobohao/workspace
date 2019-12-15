import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-system/menu//list/page',
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
    url: '/springcloud-system/menu/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-system/menu/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-system/menu/submit',
    method: 'post',
    data: row
  })
}

export const getMenu = (id) => {
  return request({
    url: '/springcloud-system/menu/detail',
    method: 'get',
    params: {
      id
    }
  })
}
export const getMenuTree = (parentId) => {
  return request({
    url: '/springcloud-system/menu/tree',
    method: 'get',
    params: {
      parentId
    }
  })
}

export const getMenuGrantTree = () => {
  return request({
    url: '/springcloud-system/menu/grant-tree',
    method: 'get'
  })
}
