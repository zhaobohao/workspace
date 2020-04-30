import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-integral/grpcls/list',
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
    url: '/springcloud-integral/grpcls/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/springcloud-integral/grpcls/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-integral/grpcls/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-integral/grpcls/submit',
    method: 'post',
    data: row
  })
}

export const selectOption = (row) => {
  return request({
    url: '/springcloud-integral/grpcls/select',
    method: 'get',
    data: row
  })
}

