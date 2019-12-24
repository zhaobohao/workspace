import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-system/area/list/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,
      descs: 'id'
    }
  })
}
export const remove = (ids) => {
  return request({
    url: '/springcloud-system/area/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-system/area/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-system/area/submit',
    method: 'post',
    data: row
  })
}

export const getarea = (id) => {
  return request({
    url: '/springcloud-system/area/detail',
    method: 'get',
    params: {
      id
    }
  })
}
export const getareaTree = (tenantId, parentId, dt) => {
  return request({
    url: '/springcloud-system/area/tree',
    method: 'get',
    params: {
      tenantId,
      parentId,
      dt
    }
  })
}
