import request from '@/utils/request'

export const getUsualList = (current, size, params) => {
  return request({
    url: '/springcloud-log/usual/list',
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}

export const getApiList = (current, size, params) => {
  return request({
    url: '/springcloud-log/api/list',
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}

export const getErrorList = (current, size, params) => {
  return request({
    url: '/springcloud-log/error/list',
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}

export const getUsualLogs = (id) => {
  return request({
    url: '/springcloud-log/usual/detail',
    method: 'get',
    params: {
      id
    }
  })
}
export const getApiLogs = (id) => {
  return request({
    url: '/springcloud-log/api/detail',
    method: 'get',
    params: {
      id
    }
  })
}
export const getErrorLogs = (id) => {
  return request({
    url: '/springcloud-log/error/detail',
    method: 'get',
    params: {
      id
    }
  })
}
