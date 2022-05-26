import request from '@/utils/request'

export function getContestProblemByCondition(condition){
    console.log(condition);
    return request.post(`/api/oj/manage/contestProblem/getContestProblemsByCondition`, condition)
}

export function deleteContestProblemByProblemId(deleteParams){
    return request.post(`/api/oj/manage/contestProblem/deleteContestProblem`, deleteParams)
}

/**
 *  @PostMapping("searchProblemsFromArchiveByCondition")
 public R searchProblemsFromArchiveByCondition(@RequestBody ManageContestProblemQueryParams manageContestProblemQueryParams){

 */
export function searchProblemsFromArchiveByCondition(condition){
    return request.post(`/api/oj/manage/contestProblem/searchProblemsFromArchiveByCondition`, condition)
}

/*
  @PostMapping("addProblem")
    public R addContestProblemById(@RequestBody  ManageContestProblemAddParams manageContestProblemAddParams){

 */
export function addContestProblemById(addPrams){
    return request.post(`/api/oj/manage/contestProblem/addContestProblem`, addPrams)
}


export function updateContestProblem(updateParams){
    return request.post(`/api/oj/manage/contestProblem/updateContestProblem`, updateParams)
}

export default {
    getContestProblemByCondition,
    deleteContestProblemByProblemId,
    searchProblemsFromArchiveByCondition,
    addContestProblemById,
    updateContestProblem
}
