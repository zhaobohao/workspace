import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api-member/ums/UmsMemberBlanceLog/list',
        method:'get',
        params:params
    })
}
export function createMemberBlanceLog(data) {
    return request({
        url:'/api-member/ums/UmsMemberBlanceLog/create',
        method:'post',
        data:data
    })
}

export function deleteMemberBlanceLog(id) {
    return request({
        url:'/api-member/ums/UmsMemberBlanceLog/delete/'+id,
        method:'get',
    })
}

export function getMemberBlanceLog(id) {
    return request({
        url:'/api-member/ums/UmsMemberBlanceLog/'+id,
        method:'get',
    })
}

export function updateMemberBlanceLog(id,data) {
    return request({
        url:'/api-member/ums/UmsMemberBlanceLog/update/'+id,
        method:'post',
        data:data
    })
}

