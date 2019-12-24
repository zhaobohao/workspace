import request from '@/utils/request'
export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-develop/tableinfo/list/page',
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
    url: '/springcloud-develop/tableinfo/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/springcloud-develop/tableinfo/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-develop/tableinfo/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-develop/tableinfo/submit',
    method: 'post',
    data: row
  })
}

export const tree = (row) => {
  return request({
    url: '/springcloud-develop/tableinfo/tree',
    method: 'get'
  })
}

export const copy = (id) => {
  return request({
    url: '/springcloud-develop/tableinfo/copy',
    method: 'post',
    params: {
      id
    }
  })
}

export const exportDdl = (ids) => {
  return request({
    url: '/springcloud-develop/generator/exportDdl',
    method: 'get',
    responseType: 'blob',
    params: {
      ids
    }
  })
}
