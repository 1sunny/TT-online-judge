import request from '@/utils/request'

export function getUserListByCondition(condition){
    return request.post(`/api/oj/manage/user/list`, condition)
}

export function updateUserRoles(userId, roles){
    return request.post(`/api/oj/manage/user/updateRoles/${userId}`, roles)
}

export default {
    getUserListByCondition,
    updateUserRoles,
}
