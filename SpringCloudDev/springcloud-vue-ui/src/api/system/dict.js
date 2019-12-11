import request from '@/utils/request'

export const getList = (current, size, params) => {
  return request({
    url: '/springcloud-system/dict/list/page',
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
    url: '/springcloud-system/dict/remove',
    method: 'post',
    params: {
      ids
    }
  })
}

export const add = (row) => {
  return request({
    url: '/springcloud-system/dict/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/springcloud-system/dict/submit',
    method: 'post',
    data: row
  })
}

export const getDict = (id) => {
  return request({
    url: '/springcloud-system/dict/detail',
    method: 'get',
    params: {
      id
    }
  })
}
export const getDictTree = () => {
  return request({
    url: '/springcloud-system/dict/tree?code=DICT',
    method: 'get'
  })
}
export const getDictionary = (code) => {
  return request({
    url: '/springcloud-system/dict/dictionary',
    method: 'get',
    params: {
      code
    }
  })
}
