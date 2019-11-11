import request from '@/utils/request'

export const resetPassword = (userIds) => {
  return request({
    url: '/springcloud-user/reset-password',
    method: 'post',
    params: {
      userIds
    }
  })
}
export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-user/list',
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
    url: '/springcloud-user/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-user/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-user/update',
    method: 'post',
    data: row
  })
}

export const getUser = (id) => {
  return request({
    url: '/springcloud-user/detail',
    method: 'get',
    params: {
      id
    }
  })
}
