import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/api-uaa/oauth/user/token',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function reg(username, password,code,confimpassword) {
    return request({
        url: '/api-uaa/oauth/user/reg',
        method: 'post',
        data: {
            username,
            confimpassword,
            code,
            password
        }
    })
}

export function getInfo() {
  return request({
    url: '/api-uaa/oauth/userinfo',
    method: 'get',
  })
}

export function logout() {
  return request({
    url: '/api-uaa/logout',
    method: 'post'
  })
}
export function codes(username) {
    return request({
        url: '/api-user/sys/sysUser/sms/codes/'+username,
        method: 'get'
    })
}

