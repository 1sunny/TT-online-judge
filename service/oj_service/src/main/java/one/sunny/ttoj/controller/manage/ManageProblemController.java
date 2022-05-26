package one.sunny.ttoj.controller.manage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import one.sunny.commonutils.MyFileUtil;
import one.sunny.commonutils.R;
import one.sunny.ttoj.pojo.params.oj.ProblemQueryParams;
import one.sunny.ttoj.pojo.vo.manage.ManageProblemVo;
import one.sunny.ttoj.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Api("admin_problem_service")
@RestController
@RequestMapping("manage/problem")
@CrossOrigin
public class ManageProblemController {
    @Autowired
    private ProblemService problemService;

    @ApiOperation(value = "带条件分页查询题目列表")
    @PostMapping("getProblemsByCondition")
    public R getProblemsByCondition(@RequestBody ProblemQueryParams problemQueryParams) {
        Map<String, Object> problemsByConditionMap = problemService.getProblemsByCondition(problemQueryParams, true);
        Long total = (Long) problemsByConditionMap.get("total");
        List<ManageProblemVo> manageProblemVos = (List<ManageProblemVo>) problemsByConditionMap.get("problems");
        return R.ok().data("problems", manageProblemVos).data("total", total);
    }

    @ApiOperation("通过题目id修改题目是否可见")
    @PostMapping("updateProblemVisibility/{problemId}/{visible}")
    public R updateProblemVisibility(@PathVariable("problemId") Long problemId,
                                     @PathVariable("visible") Boolean visible) {
        Boolean hasChange = problemService.updateProblemVisibility(problemId, visible);
        if (hasChange){
            return R.ok().message("修改成功");
        }
        return R.ok().message("修改失败");
    }

    @ApiOperation("通过id获得单个题目")
    @GetMapping("{id}")
    public R getProblemById(@PathVariable("id") Long id) {
        ManageProblemVo problem = problemService.getProblemById(id, true);
        return R.ok().data("problem", problem);
    }

    @ApiOperation("保存题目")
    @PreAuthorize("@problemExpression.addProblem()")
    @PostMapping("save")
    public R saveProblem(@RequestParam("testcase") MultipartFile testcase,
                         @RequestParam("adminProblemSaveParams") String adminProblemSaveParams) throws IOException {
        File testcaseFile = MyFileUtil.MultipartFileToFile(testcase);
        Boolean save = problemService.saveProblem(testcaseFile, adminProblemSaveParams);
        if (save) {
            return R.ok().message("保存成功");
        }
        return R.error().message("保存失败");
    }

    @ApiOperation("修改题目")
    @PreAuthorize("@problemExpression.updateProblem(#adminProblemSaveParams)")
    @PostMapping("update")
    public R updateProblem(@RequestParam(value = "testcase", required = false) MultipartFile testcase,
                           @RequestParam("adminProblemSaveParams") String adminProblemSaveParams) throws IOException {
        File testcaseFile = MyFileUtil.MultipartFileToFile(testcase);
        Boolean update = problemService.updateProblem(testcaseFile, adminProblemSaveParams);
        if (update) {
            return R.ok().message("修改成功");
        }
        return R.ok().message("修改失败");
    }

    @ApiOperation("根据problemId删除题目")
    @PreAuthorize("@problemExpression.deleteProblem(#problemId)")
    @PostMapping("delete/{problemId}")
    public R deleteProblem(@PathVariable("problemId") Long problemId){
        boolean remove = problemService.removeById(problemId);
        if (remove){
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }
}
