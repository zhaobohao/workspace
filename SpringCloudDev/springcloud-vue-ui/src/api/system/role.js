import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-system/role/list',
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}
export const grantTree = () => {
  return request({
    url: '/springcloud-system/menu/grant-tree',
    method: 'get'
  })
}

export const grant = (roleIds, menuIds) => {
  return request({
    url: '/springcloud-system/role/grant',
    method: 'post',
    params: {
      roleIds,
      menuIds
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/springcloud-system/role/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-system/role/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-system/role/submit',
    method: 'post',
    data: row
  })
}

export const getRole = (roleIds) => {
  return request({
    url: '/springcloud-system/menu/role-tree-keys',
    method: 'get',
    params: {
      roleIds
    }
  })
}

export const getRoleTree = (tenantId) => {
  return request({
    url: '/springcloud-system/role/tree',
    method: 'get',
    params: {
      tenantId
    }
  })
}
