package one.sunny.ttoj.controller.oj;


import io.swagger.annotations.ApiOperation;
import one.sunny.commonutils.R;
import one.sunny.ttoj.pojo.params.oj.ContestRankingsParams;
import one.sunny.ttoj.pojo.vo.oj.ContestRankVo;
import one.sunny.ttoj.service.ContestRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/contest-rank")
@CrossOrigin
public class ContestRankController {
    @Autowired
    private ContestRankService contestRankService;

    @ApiOperation("通过id获取比赛排名")
    @PostMapping("rankings")
    public R getRankingsByCondition(@RequestBody ContestRankingsParams contestRankingsParams){
        Map<String, Object> rankingsByCondition = contestRankService.getRankingsByCondition(contestRankingsParams);
        ContestRankVo contestRankVo = (ContestRankVo) rankingsByCondition.get("contestRank");
        long total = (long) rankingsByCondition.get("total");
        return R.ok().data("contestRank", contestRankVo).data("total", total);
    }

}

