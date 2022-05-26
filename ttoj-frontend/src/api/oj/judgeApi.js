import request from '@/utils/request'

export function archiveJudge(params){
    return request.post('/api/oj/judge/archive', params)
}

export function contestSubmit(params){
    return request.post('/api/oj/judge/contestSubmit', params)
}

export default {
    archiveJudge,
    contestSubmit
}
