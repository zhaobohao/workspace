import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-mock-server/mockwebsite/list/page',
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
    url: '/springcloud-mock-server/mockwebsite/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/springcloud-mock-server/mockwebsite/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-mock-server/mockwebsite/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-mock-server/mockwebsite/submit',
    method: 'post',
    data: row
  })
}

export const selectOption = (row) => {
  return request({
    url: '/springcloud-mock-server/mockwebsite/select',
    method: 'get',
    data: row
  })
}
export const tree = (row) => {
  return request({
    url: '/springcloud-mock-server/mockwebsite/tree',
    method: 'get'
  })
}
