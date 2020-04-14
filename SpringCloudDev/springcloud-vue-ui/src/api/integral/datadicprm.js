import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-integral/datadicprm/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
      descs: 'id'
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/springcloud-integral/datadicprm/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/springcloud-integral/datadicprm/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-integral/datadicprm/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-integral/datadicprm/submit',
    method: 'post',
    data: row
  })
}

export const selectOption = (row) => {
  return request({
    url: '/springcloud-integral/datadicprm/select',
    method: 'get',
    data: row
  })
}

export const getTreeData = (tenantId, parentId, dt) => {
  return request({
    url: '/springcloud-integral/datadicprm/tree',
    method: 'get',
    params: {
      tenantId,
      parentId,
      dt
    }
  })
}
