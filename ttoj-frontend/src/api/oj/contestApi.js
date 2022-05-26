import request from '@/utils/request'

export function getContestByContestId(contestId){
    return request.get(`/api/oj/contest/${contestId}`)
}

export function getContestsByCondition(condition) {
    return request.post('/api/oj/contest/getContestsByCondition', condition)
}

export function getRecentContest(days){
    return request.get(`/api/oj/contest/recent/${days}`)
}

export default {
    getContestByContestId,
    getContestsByCondition,
    getRecentContest
}
