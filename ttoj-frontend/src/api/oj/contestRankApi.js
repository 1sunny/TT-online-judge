import request from '@/utils/request'

export function getContestRankingsByCondition(condition){
    return request.post(`/api/oj/contest-rank/rankings`, condition)
}

export function registerForContest(registerParams){
    return request.post('/api/oj/contest-rank/register', registerParams);
}

export function getContestRegisterStatus(contestId, userId){
    return request.get(`/api/oj/contest-rank/${contestId}/${userId}`);
}

export default {
    getContestRankingsByCondition,
    registerForContest,
    getContestRegisterStatus
}
