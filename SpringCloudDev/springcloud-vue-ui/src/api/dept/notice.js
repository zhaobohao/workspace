import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-desk/notice/list',
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
    url: '/springcloud-desk/notice/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-desk/notice/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-desk/notice/submit',
    method: 'post',
    data: row
  })
}

export const getNotice = (id) => {
  return request({
    url: '/springcloud-desk/notice/detail',
    method: 'get',
    params: {
      id
    }
  })
}
