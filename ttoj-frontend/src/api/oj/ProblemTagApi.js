import request from '@/utils/request'

export function getProblemTag(){
    return request.get('/api/oj/problem-tag/tree')
}

export function getProblemTagsByProblemId(problemId){
    return request.get(`/api/oj/problem-tag/${problemId}/tags`)
}
export default {
    getProblemTag,
    getProblemTagsByProblemId
}
