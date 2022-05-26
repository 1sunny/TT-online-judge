import request from '@/utils/request'

export function getCurrentUser(){
    return request.get('/api/oj/user/currentUser')
}

export function getRankListByCondition(condition){
    return request.post(`/api/oj/user/rank`, condition)
}

export default {
    getCurrentUser,
    getRankListByCondition
}

