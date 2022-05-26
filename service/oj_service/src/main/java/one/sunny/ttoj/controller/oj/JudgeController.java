package one.sunny.ttoj.controller.oj;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.sunny.commonutils.R;
import one.sunny.ttoj.pojo.params.oj.ArchiveJudgeParams;
import one.sunny.ttoj.pojo.params.oj.ContestSubmitParams;
import one.sunny.ttoj.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("judge_service")
@RestController
@RequestMapping("/judge")
@CrossOrigin
public class JudgeController {
    @Autowired
    private JudgeService judgeService;

    @ApiOperation("")
    @PostMapping("archive")
    public R problemArchiveJudge(@RequestBody ArchiveJudgeParams archiveJudgeParams){
        System.out.println(archiveJudgeParams);
        return judgeService.problemArchiveJudge(archiveJudgeParams);
    }

    @ApiOperation("用户在比赛中提交代码")
    @PostMapping("contestSubmit")
    public R contestSubmit(@RequestBody ContestSubmitParams contestSubmitParams) {
        return judgeService.contestSubmit(contestSubmitParams);
    }
}
