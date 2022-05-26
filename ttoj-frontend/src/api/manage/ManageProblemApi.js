import request from '@/utils/request'

export function getProblemsByCondition(condition) {
    return request.post('/api/oj/manage/problem/getProblemsByCondition', condition)
}

export function getProblemById(id){
    return request.get('/api/oj/manage/problem/' + id)
}

export function saveProblem(formData, config){
    return request.post('/api/oj/manage/problem/save', formData, config)
}

export function updateProblem(formData, config){
    return request.post(`/api/oj/manage/problem/update`, formData, config)
}

export function updateProblemVisibility(problemId, visible){
    return request.post(`/api/oj/manage/problem/updateProblemVisibility/${problemId}/${visible}`)
}

/*
   @ApiOperation("根据problemId删除题目")
    @PostMapping("delete/${problemId}")
    public R deleteProblem(@PathVariable("problemId") Long problemId){
 */
export function deleteProblem(problemId){
    return request.post(`/api/manage/delete/${problemId}`)
}

export default {
    getProblemsByCondition,
    getProblemById,
    updateProblemVisibility,
    saveProblem,
    updateProblem,
    deleteProblem
}
