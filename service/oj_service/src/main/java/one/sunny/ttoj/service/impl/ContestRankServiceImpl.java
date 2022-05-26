package one.sunny.ttoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.ttoj.entity.ContestProblem;
import one.sunny.ttoj.entity.ContestRank;
import one.sunny.ttoj.entity.ContestUserProblem;
import one.sunny.ttoj.mapper.ContestRankMapper;
import one.sunny.ttoj.pojo.params.oj.ContestRankingsParams;
import one.sunny.ttoj.pojo.vo.oj.ContestProblemVo;
import one.sunny.ttoj.pojo.vo.oj.ContestRankVo;
import one.sunny.ttoj.pojo.vo.oj.ContestUserRankVo;
import one.sunny.ttoj.service.ContestProblemService;
import one.sunny.ttoj.service.ContestRankService;
import one.sunny.ttoj.service.ContestSubmissionService;
import one.sunny.ttoj.service.ContestUserProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContestRankServiceImpl extends ServiceImpl<ContestRankMapper, ContestRank> implements ContestRankService {
    @Autowired
    private ContestProblemService contestProblemService;
    @Autowired
    private ContestSubmissionService contestSubmissionService;
    @Autowired
    private ContestRankMapper contestRankMapper;
    @Autowired
    private ContestUserProblemService contestUserProblemService;

    @Override
    public Map<String, Object> getRankingsByCondition(ContestRankingsParams contestRankingsParams) {
        Long contestId = contestRankingsParams.getContestId();
        Integer currentPage = contestRankingsParams.getCurrentPage();
        Integer pageSize = contestRankingsParams.getPageSize();
        List<ContestUserRankVo> rankVoList = new ArrayList<>();

        // 获得所有 visible = true 的 contest problem 按 displayId 升序
        //List<Problem> contestProblems = contestProblemService.getProblemsByContestIdOrderByDisplayId(contestId);
        List<ContestProblem> contestProblems = contestProblemService.getContestProblemsByContestIdOrderByDisplayId(contestId, true);
        LambdaQueryWrapper<ContestRank> contestRankWrapper = new LambdaQueryWrapper<>();
        // 根据题目数降序, 罚时升序排序
        contestRankWrapper.orderByDesc(ContestRank::getAcNum)
                .orderByAsc(ContestRank::getTotalTime);

        List<ContestProblemVo> contestProblemVos = copyList(contestProblems);
        Page<ContestRank> page = new Page<>(currentPage, pageSize);
        contestRankMapper.selectPage(page, contestRankWrapper);
        List<ContestRank> rankList = page.getRecords();
        long total = page.getTotal();
        for (ContestRank contestRank : rankList){
            Long userId = contestRank.getUserId();
            ContestUserRankVo contestUserRankVo = new ContestUserRankVo();

            Map<String,String> firstAcTimeMap = new HashMap<>();
            Map<String,Boolean> firstBloodMap = new HashMap<>();
            Map<String,Integer> waTimesMap = new HashMap<>();
            for (ContestProblem contestProblem : contestProblems){
                String problemDisplayId = contestProblem.getProblemDisplayId();
                Long problemId = contestProblem.getProblemId();
                LambdaQueryWrapper<ContestUserProblem> contestUserProblemWrapper = new LambdaQueryWrapper<>();
                contestUserProblemWrapper.eq(ContestUserProblem::getContestId, contestId)
                        .eq(ContestUserProblem::getProblemId, problemId)
                        .eq(ContestUserProblem::getUserId, userId);
                ContestUserProblem contestUserProblem = contestUserProblemService.getOne(contestUserProblemWrapper);
                if (contestUserProblem != null){
                    Integer firstAcTime = contestUserProblem.getFirstAcTime();
                    if (firstAcTime != null){
                        firstAcTimeMap.put(problemDisplayId, String.valueOf(firstAcTime));
                    }else{
                        firstAcTimeMap.put(problemDisplayId, null);
                    }
                    Boolean firstBlood = contestUserProblem.getFirstBlood();
                    firstBloodMap.put(problemDisplayId, firstBlood);
                    Integer waTimes = contestUserProblem.getWaTimes();
                    waTimesMap.put(problemDisplayId, waTimes);
                }else{
                    firstAcTimeMap.put(problemDisplayId, null);
                    firstBloodMap.put(problemDisplayId, false);
                    waTimesMap.put(problemDisplayId, 0);
                }
            }

            contestUserRankVo.setAcNum(contestRank.getAcNum());
            contestUserRankVo.setTotalTime(contestRank.getTotalTime());
            contestUserRankVo.setUserId(userId);
            contestUserRankVo.setUsername(contestRank.getUsername());
            contestUserRankVo.setFirstAcTime(firstAcTimeMap);
            contestUserRankVo.setIsFirstBlood(firstBloodMap);
            contestUserRankVo.setWaTimes(waTimesMap);
            rankVoList.add(contestUserRankVo);
        }
        ContestRankVo contestRankVo = new ContestRankVo();
        contestRankVo.setUserRanks(rankVoList);
        contestRankVo.setContestProblems(contestProblemVos);
        Map<String, Object> contestRankByConditionMap = new HashMap<>();
        contestRankByConditionMap.put("contestRank", contestRankVo);
        contestRankByConditionMap.put("total", total);
        return contestRankByConditionMap;
    }

    private List<ContestProblemVo> copyList(List<ContestProblem> contestProblems) {
        List<ContestProblemVo> list = new ArrayList<>();
        for (ContestProblem contestProblem : contestProblems){
            list.add(copy(contestProblem));
        }
        return list;
    }

    private ContestProblemVo copy(ContestProblem contestProblem) {
        ContestProblemVo contestProblemVo = new ContestProblemVo();
        BeanUtils.copyProperties(contestProblem, contestProblemVo);
        return contestProblemVo;
    }

}
