import request from '@/utils/request'

export function getProblemsByCondition(condition) {
    return request.post('/api/oj/problem/getProblemsByCondition', condition)
}

export function getProblemById(id){
    return request.get('/api/oj/problem/' + id)
}

export default {
    getProblemsByCondition,
    getProblemById,
}
