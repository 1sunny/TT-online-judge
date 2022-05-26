package one.sunny.ttoj.controller.manage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.sunny.commonutils.R;
import one.sunny.ttoj.entity.Contest;
import one.sunny.ttoj.pojo.params.manage.ManageContestAuthorUpdateParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestAuthorUserSearchParams;
import one.sunny.ttoj.pojo.params.oj.ContestQueryParams;
import one.sunny.ttoj.pojo.params.oj.ManageContestCreateParams;
import one.sunny.ttoj.pojo.vo.manage.ManageContestAuthorVo;
import one.sunny.ttoj.pojo.vo.manage.ManageContestVo;
import one.sunny.ttoj.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Api("admin_contest_service")
@RestController
@RequestMapping("manage/contest")
@CrossOrigin
public class ManageContestController {
    @Autowired
    private ContestService contestService;

    @ApiOperation(value = "带条件分页查询比赛列表")
    @PostMapping("getContestsByCondition")
    public R getContestsByCondition(@Validated @RequestBody ContestQueryParams contestQueryParams){
        Map<String, Object> contestsByConditionMap = contestService.getContestsByCondition(contestQueryParams, true);
        Long total = (Long) contestsByConditionMap.get("total");
        List<ManageContestVo> manageContestVos = (List<ManageContestVo>) contestsByConditionMap.get("contests");
        return R.ok().data("contests", manageContestVos).data("total", total);
    }

    @ApiOperation("通过题目id修改题目是否可见")
    @PreAuthorize("@contestExpression.updateContest(#contestId)")
    @PostMapping("changeContestVisibility/{contestId}/{visible}")
    public R changeContestVisibility(@PathVariable("contestId") Long contestId,
                                     @PathVariable("visible") Boolean visible){
        Boolean change = contestService.changeContestVisibility(contestId, visible);
        if (change){
            return R.ok().message("修改成功");
        }
        return R.ok().message("修改失败");
    }

    @ApiOperation("通过比赛id获取具体比赛")
    @GetMapping("{id}")
    public R getContestById(@PathVariable("id") Long id){
        ManageContestVo manageContestVo = contestService.getContestById(id, true);
        return R.ok().data("contest", manageContestVo);
    }

    @ApiOperation("创建比赛")
    @PostMapping("save")
    @PreAuthorize("@contestExpression.createContest()")
    public R saveContest(@Validated @RequestBody ManageContestCreateParams manageContestCreateParams){
        Boolean save = contestService.saveContest(manageContestCreateParams);
        if (save){
            return R.ok().message("保存成功");
        }
        return R.ok().message("保存失败");
    }

    @ApiOperation("修改比赛")
    @PreAuthorize("@contestExpression.updateContest(#contest)")
    @PostMapping("update")
    public R updateContest(@Validated @RequestBody Contest contest){
        Boolean update = contestService.updateContest(contest);
        if (update){
            return R.ok().message("更新成功");
        }
        return R.ok().message("更新失败");
    }

    @ApiOperation("删除比赛")
    @PreAuthorize("@contestExpression.deleteContest(#contestId)")
    @PostMapping("delete")
    public R deleteContest(@RequestBody @NotNull Long contestId){
        Boolean deleted = contestService.deleteContestByContestId(contestId);
        if (deleted){
            return R.ok().message("删除成功");
        }
        return R.ok().message("删除失败");
    }

    @ApiOperation("获得可以作为出题人的用户列表")
    @PostMapping("getContestAuthorUsersBySearch")
    public R getContestAuthorUsersBySearch(@Validated @RequestBody ManageContestAuthorUserSearchParams manageContestAuthorUserSearchParams){
        List<ManageContestAuthorVo> contestAuthors = contestService.getContestAuthorUsersBySearch(manageContestAuthorUserSearchParams);
        return R.ok().data("contestAuthors", contestAuthors);
    }

    @ApiOperation("更新比赛作者")
    @PostMapping("updateContestAuthors")
    public R updateContestAuthors(@Validated @RequestBody ManageContestAuthorUpdateParams manageContestAuthorUpdateParams){
        boolean update = contestService.updateContestAuthors(manageContestAuthorUpdateParams);
        return R.ok().message("更新成功");
    }
}

