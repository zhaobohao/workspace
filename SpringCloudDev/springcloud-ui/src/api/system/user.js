import request from '@/router/axios';

export const resetPassword = (userIds) => {
  return request({
    url: '/api/springcloud-user/reset-password',
    method: 'post',
    params: {
      userIds,
    }
  })
}
export const getList = (current, size, params) => {
  return request({
    url: '/api/springcloud-user/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const remove = (ids) => {
  return request({
    url: '/api/springcloud-user/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/springcloud-user/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/springcloud-user/update',
    method: 'post',
    data: row
  })
}

export const getUser = (id) => {
  return request({
    url: '/api/springcloud-user/detail',
    method: 'get',
    params: {
      id,
    }
  })
}
