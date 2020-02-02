import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/build/groupMember/list',
        method:'get',
        params:params
    })
}
export function createGroupMember(data) {
    return request({
        url:'/build/groupMember/create',
        method:'post',
        data:data
    })
}

export function deleteGroupMember(id) {
    return request({
        url:'/build/groupMember/delete/'+id,
        method:'get',
    })
}

export function getGroupMember(id) {
    return request({
        url:'/build/groupMember/'+id,
        method:'get',
    })
}

export function updateGroupMember(id,data) {
    return request({
        url:'/build/groupMember/update/'+id,
        method:'post',
        data:data
    })
}

