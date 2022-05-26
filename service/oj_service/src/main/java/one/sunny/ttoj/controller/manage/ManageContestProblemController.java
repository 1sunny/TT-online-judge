package one.sunny.ttoj.controller.manage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.sunny.commonutils.R;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemAddParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemUpdateParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemDeleteParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemQueryParams;
import one.sunny.ttoj.pojo.vo.manage.ManageContestProblemVo;
import one.sunny.ttoj.pojo.vo.manage.ManageSearchProblemVo;
import one.sunny.ttoj.service.ContestProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("admin_contest_problem_service")
@RestController
@RequestMapping("manage/contestProblem")
@CrossOrigin
public class ManageContestProblemController {
    @Autowired
    private ContestProblemService contestProblemService;

    @ApiOperation("改变比赛题目displayId或者visibility")
    @PreAuthorize("@contestProblemExpression.updateContestProblem(#manageContestProblemUpdateParams)")
    @PostMapping("updateContestProblem")
    public R changeContestProblem(@RequestBody ManageContestProblemUpdateParams manageContestProblemUpdateParams){
        Boolean change = contestProblemService.updateContestProblem(manageContestProblemUpdateParams);
        if (change){
            return R.ok().message("修改成功");
        }
        return R.ok().message("修改失败");
    }

    @ApiOperation("通过Condition获得比赛题目")
    @PreAuthorize("@contestProblemExpression.viewManageContestProblem(#manageContestProblemQueryParams)")
    @PostMapping("getContestProblemsByCondition")
    public R getContestProblemsByCondition(@RequestBody ManageContestProblemQueryParams manageContestProblemQueryParams){
        Map<String, Object> map = contestProblemService.getManageContestProblemsByCondition(manageContestProblemQueryParams);
        List<ManageContestProblemVo> contestProblemVos = (List<ManageContestProblemVo>) map.get("manageContestProblemVos");
        int total = (int) map.get("total");
        return R.ok().data("contestProblems", contestProblemVos).data("total", total);
    }

    @ApiOperation("通过Condition查询题库题目(不包含当前比赛已经有的题目)")
    @PostMapping("searchProblemsFromArchiveByCondition")
    public R searchProblemsFromArchiveByCondition(@RequestBody ManageContestProblemQueryParams manageContestProblemQueryParams){
        Map<String, Object> map = contestProblemService.searchProblemsFromArchiveByCondition(manageContestProblemQueryParams);
        List<ManageSearchProblemVo> searchProblemVos = (List<ManageSearchProblemVo>) map.get("manageSearchProblemVos");
        long total = (long) map.get("total");
        return R.ok().data("problems", searchProblemVos).data("total", total);
    }

    @ApiOperation("通过比赛contestId给比赛增加题目(problemId)")
    @PreAuthorize("@contestProblemExpression.addContestProblem(#manageContestProblemAddParams)")
    @PostMapping("addContestProblem")
    public R addContestProblemById(@RequestBody ManageContestProblemAddParams manageContestProblemAddParams){
        boolean add = contestProblemService.addContestProblemByProblemId(manageContestProblemAddParams);
        if (add){
            return R.ok().message("添加成功");
        }
        return R.error().message("添加失败");
    }

    @ApiOperation("通过problemId删除contestProblem")
    @PreAuthorize("@contestProblemExpression.deleteContestProblem(#manageContestProblemDeleteParams)")
    @PostMapping("deleteContestProblem")
    public R deleteContestProblemByProblemId(@RequestBody ManageContestProblemDeleteParams manageContestProblemDeleteParams){
        boolean delete = contestProblemService.deleteContestProblemByProblemId(manageContestProblemDeleteParams);
        if (delete){
            return R.ok().message("删除成功");
        }
        return R.ok().message("删除失败");
    }
}
