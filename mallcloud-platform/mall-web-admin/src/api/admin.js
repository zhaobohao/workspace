import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/api-user/list',
    method:'get',
    params:params
  })
}
export function userRoles(params,adminId) {
  return request({
    url:'/api-user/role/'+adminId,
    method:'get',
    params:params
  })
}
export function userRoleCheck(params) {
  return request({
    url:'/api-user/userRoleCheck',
    method:'get',
    params:params
  })
}
export function createAdmin(data) {
  return request({
    url:'/api-user/register',
    method:'post',
    data:data
  })
}
export function updateShowStatus(data) {
  return request({
    url:'/api-user/sys/sysUser/update/showStatus',
    method:'post',
    data:data
  })
}

export function updateFactoryStatus(data) {
  return request({
    url:'/api-user/update/factoryStatus',
    method:'post',
    data:data
  })
}

export function deleteAdmin(id) {
  return request({
    url:'/api-user/delete/'+id,
    method:'get',
  })
}

export function getAdmin(id) {
  return request({
    url:'/api-user/'+id,
    method:'get',
  })
}

export function updateAdmin(id,data) {
  return request({
    url:'/api-user/update/'+id,
    method:'post',
    data:data
  })
}

export function updatePassword(data) {
  return request({
    url:'/api-user/sys/sysUser/updatePassword',
    method:'post',
    data:data
  })
}


export function communityList(id) {
  return request({
    url:'/api-user/sys/sysUser/community/'+id,
    method:'get'
  })
}

export function communityUser(id) {
  return request({
    url:'/api-user/sys/sysUser/communityUser/'+id,
    method:'get'
  })
}
export function userCommunityRelate(data) {
  return request({
    url:'/api-user/sys/sysUser/userCommunityRelate',
    method:'post',
    data:data
  })
}
