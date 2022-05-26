package one.sunny.ttoj.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import one.sunny.commonutils.Constants;
import one.sunny.commonutils.R;
import one.sunny.ttoj.mapper.ProblemMapper;
import one.sunny.ttoj.pojo.dto.TestCaseResultsDto;
import one.sunny.ttoj.entity.*;
import one.sunny.ttoj.service.*;
import one.sunny.ttoj.pojo.params.oj.ContestSubmitParams;
import one.sunny.ttoj.pojo.params.oj.JudgeParams;
import one.sunny.ttoj.pojo.vo.oj.JudgeResultVo;
import one.sunny.ttoj.pojo.params.oj.ArchiveJudgeParams;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${judge.host}")
    private String judgeHost;
    @Value("${judge.port}")
    private String judgePort;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private ContestRankService contestRankService;
    @Autowired
    private JudgeService judgeService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private ContestProblemService contestProblemService;
    @Autowired
    private ContestUserProblemService contestUserProblemService;
    @Autowired
    private ContestSubmissionService contestSubmissionService;
    @Autowired
    private UserAcProblemService userAcProblemService;
    @Autowired
    ProblemMapper problemMapper;

    @Override
    public R judge(JudgeParams judgeParams) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> verifyParams = new HashMap<String, Object>();
        verifyParams.put("language_config", judgeParams.getLanguage_config());
        verifyParams.put("max_cpu_time", judgeParams.getMax_cpu_time());
        verifyParams.put("max_memory", judgeParams.getMax_memory() * 1024 * 1024);
        verifyParams.put("output", judgeParams.getOutput());
        verifyParams.put("src", judgeParams.getSrc());
        verifyParams.put("test_case_id", judgeParams.getTest_case_id());
        headers.add("X-Judge-Server-Token", "c2333a7e3a607935c67c1e6f6810395decc9f66f592b812aaada7db94ba215d6");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(verifyParams, headers);
        JudgeResultVo judgeResultVo = restTemplate.postForObject("http://" + judgeHost + ":" + judgePort + "/judge", entity, JudgeResultVo.class);
/*
问题记录:
postForObject返回的ResultVo里面的data可能是编译错误string,也可能是list
 */
        if (judgeResultVo.getErr() != null) {
            return R.ok().data("result", judgeResultVo.getErr())
                    .data("errorReason", judgeResultVo.getData())
                    .data("timeUse", null)
                    .data("memoryUse", null);
        }

        String json = JSONObject.toJSONString(judgeResultVo.getData());
        List<TestCaseResultsDto> data =  JSON.parseArray(json, TestCaseResultsDto.class);
        Integer wrongReason = 0;
        Integer timeUse = 0;
        Integer memoryUse = 0;
        for(TestCaseResultsDto result : data){
            timeUse = Math.max(timeUse, result.getCpu_time());
            memoryUse = Math.max(memoryUse, result.getMemory());
            if (result.getResult() != 0){
                wrongReason = result.getResult();
                break;
            }
        }
        String message = "";
        if (wrongReason == 0){
            message = Constants.Accepted;
        }else if(wrongReason == -1){
            message = Constants.WrongAnswer;
        }else if(wrongReason == 1){
            message = Constants.TimeLimitExceeded;
        }else if(wrongReason == 2){
            message = Constants.RealTimeLimitExceeded;
        }else if(wrongReason == 3){
            message = Constants.MemoryLimitExceeded;
        }else if(wrongReason == 4){
            message = Constants.RuntimeError;
        }else if(wrongReason == 5){
            message = Constants.SystemError;
        }else{

        }

        return R.ok()
                .data("result", message)
                .data("timeUse", timeUse)
                .data("memoryUse", memoryUse)
                .data("errorReason", null);
    }

    @Override
    public R problemArchiveJudge(ArchiveJudgeParams archiveJudgeParams) {
        Submission submission = new Submission();
        submission.setSubmitTime(new Date());

        Long userId = archiveJudgeParams.getUserId();
        String username = archiveJudgeParams.getUsername();
        Long problemId = archiveJudgeParams.getProblemId();
        String problemName = archiveJudgeParams.getProblemName();
        String code = archiveJudgeParams.getCode();
        String language = archiveJudgeParams.getLanguage();
        Problem problem = problemService.getById(problemId);
        String ip = archiveJudgeParams.getIp();

        if(problem == null){
            return R.error().message("题目不存在");
        }
        JudgeParams judgeParams = new JudgeParams(language,
                code,
                problem.getTimeLimit(),
                problem.getMemoryLimit(),
                problem.getTestCaseDir(),
                false);
        R judgeResult = judge(judgeParams);
        Map<String, Object> judgeResultData = judgeResult.getData();
        String result = (String) judgeResultData.get("result");
        Integer timeUse = (Integer) judgeResultData.get("timeUse");
        Integer memoryUse = (Integer) judgeResultData.get("memoryUse");
        submission.setUserId(userId);
        submission.setUsername(username);
        submission.setProblemId(problemId);
        submission.setProblemName(problemName);
        submission.setCode(code);
        submission.setResult(result);
        submission.setLanguage(language);
        submission.setTimeUse(timeUse);
        submission.setMemoryUse(memoryUse);
        submission.setIp(ip);
        // 进行一些和用户数据有关的操作
        LambdaUpdateWrapper<Problem> problemUpdateWrapper = new LambdaUpdateWrapper<>();
        problemUpdateWrapper.eq(Problem::getId, problemId)
                .setSql("submit_num = submit_num + 1");
        if (Constants.Accepted.equals(result)){
            userAcProblemService.save(new UserAcProblem(null, userId, problemId));
            problemUpdateWrapper.setSql("ac_num = ac_num + 1");
        }
        problemMapper.update(null, problemUpdateWrapper);
        boolean save = submissionService.save(submission);
        if (save){
            return judgeResult;
        }else{
            return R.error().message("submission保存失败");
        }
    }

    public ContestSubmission copy(ContestSubmitParams contestSubmitParams){
        ContestSubmission contestSubmission = new ContestSubmission();
        BeanUtils.copyProperties(contestSubmitParams, contestSubmission);
        return contestSubmission;
    }

    /*
    1. 判断该提交的参数是否都有
    2. 是否注册
    3. 是否在比赛时间内
    4.
     */
    @Override
    public R contestSubmit(ContestSubmitParams contestSubmitParams) {
        System.out.println(contestSubmitParams);
        Long userId = contestSubmitParams.getUserId();
        String username = contestSubmitParams.getUsername();
        Long contestId = contestSubmitParams.getContestId();
        Long problemId = contestSubmitParams.getProblemId();
        String problemDisplayId = contestSubmitParams.getProblemDisplayId();
        String code = contestSubmitParams.getCode();
        String language = contestSubmitParams.getLanguage();
        String ip = contestSubmitParams.getIp();

        DateTime submitTime = new DateTime();
        // 是否太早或者太晚
        LambdaQueryWrapper<Contest> contestWrapper = new LambdaQueryWrapper<>();
        contestWrapper.eq(Contest::getId, contestId);
        Contest contest = contestService.getOne(contestWrapper);
        if (contest == null){
            return R.error().message("这是一次无效的提交, 比赛不存在");
        }
        DateTime startTime = new DateTime(contest.getStartTime());
        DateTime endTime = new DateTime(contest.getEndTime());
        if (submitTime.isBefore(startTime)){
            return R.error().message("比赛还未开始");
        }
        if (submitTime.isAfter(endTime)){
            return R.error().message("比赛已经结束");
        }
        LambdaQueryWrapper<ContestRank> contestRankWrapper = new LambdaQueryWrapper<>();
        contestRankWrapper.eq(ContestRank::getContestId, contestId)
                .eq(ContestRank::getUserId, userId);
        ContestRank contestRank = contestRankService.getOne(contestRankWrapper);
        boolean hadGetContestRank = true;
        if (contestRank == null){
            contestRank = new ContestRank();
            hadGetContestRank = false;
            contestRank.setUserId(userId);
            contestRank.setUsername(username);
            contestRank.setContestId(contestId);
            contestRank.setTotalTime(0);
            contestRank.setAcNum(0);
        }

        ContestSubmission contestSubmission = copy(contestSubmitParams);
        // 判题
        Problem problem = problemService.getById(problemId);
        if (problem == null){
            return R.error().message("这是一次无效的提交, 题目不存在");
        }
        // TODO 语言
        JudgeParams judgeParams = new JudgeParams(
                "cpp",
                code,
                problem.getTimeLimit(),
                problem.getMemoryLimit(),
                problem.getTestCaseDir(),
                false
        );
        R judgeResult = judgeService.judge(judgeParams);
        Map<String, Object> judgeResultData = judgeResult.getData();
        contestSubmission.setProblemName(problem.getName());
        contestSubmission.setResult((String) judgeResultData.get("result"));
        contestSubmission.setTimeUse((Integer) judgeResultData.get("timeUse"));
        contestSubmission.setMemoryUse((Integer) judgeResultData.get("memoryUse"));
        contestSubmission.setSubmitTime(submitTime.toDate());
        contestSubmission.setCodeLength(code.getBytes().length);
        // 更新 t_contest_problem
        LambdaQueryWrapper<ContestProblem> contestProblemWrapper = new LambdaQueryWrapper<>();
        contestProblemWrapper.eq(ContestProblem::getContestId, contestId)
                .eq(ContestProblem::getProblemId, problemId)
                .eq(ContestProblem::getProblemDisplayId, problemDisplayId);
        ContestProblem contestProblem = contestProblemService.getOne(contestProblemWrapper);
        if (contestProblem == null){
            return R.error().message("contestProblem == null");
        }
        contestProblem.setSubmitNum(contestProblem.getSubmitNum() + 1);
        // 获取 user-problem
        LambdaQueryWrapper<ContestUserProblem> contestUserProblemWrapper = new LambdaQueryWrapper<>();
        contestUserProblemWrapper.eq(ContestUserProblem::getContestId, contestId)
                .eq(ContestUserProblem::getProblemId, problemId)
                .eq(ContestUserProblem::getUserId, userId);
        Boolean hadGetContestUserProblem = true;
        ContestUserProblem contestUserProblem = contestUserProblemService.getOne(contestUserProblemWrapper);
        // 有可能没有(即用户第一次提交)
        if (contestUserProblem == null){
            hadGetContestUserProblem = false;
            // 附上初始值
            contestUserProblem = new ContestUserProblem();
            contestUserProblem.setUserId(userId);
            contestUserProblem.setContestId(contestId);
            contestUserProblem.setProblemId(problemId);
            contestUserProblem.setWaTimes(0);
            contestUserProblem.setFirstAcTime(null);
            contestUserProblem.setFirstBlood(false);
        }
        // -----------------
        Integer firstAcTime = contestUserProblem.getFirstAcTime();
        if (Constants.Accepted.equals((String)judgeResultData.get("result"))){
            contestProblem.setAcNum(contestProblem.getAcNum() + 1);
            // 是否一血
            int distant = Minutes.minutesBetween(startTime, submitTime).getMinutes();
            Long firstAcUserId = contestProblem.getFirstAcUserId();
            if (firstAcUserId == null){
                contestProblem.setFirstAcUserId(userId);
                contestProblem.setFirstAcUsername(username);
                contestProblem.setProblemFirstAcTime(distant);
                contestUserProblem.setFirstBlood(true);
            }
            // 该用户此前是否通过该题, 若不是则计算罚时
            if (firstAcTime == null){
                // TODO 罚时由创建时决定
                contestUserProblem.setFirstAcTime(distant);
                int penalty = distant + contestUserProblem.getWaTimes() * 20;
                contestRank.setTotalTime(contestRank.getTotalTime() + penalty);
                contestRank.setAcNum(contestRank.getAcNum() + 1);
                contestUserProblem.setFirstAcTime(distant);
            }
        }else{
            String errorReason = (String) judgeResultData.get("errorReason");
            if (errorReason == null && firstAcTime == null){
                // 若不是系统故障或者编译错误并且未AC, 增加用户对本题的错误次数
                contestUserProblem.setWaTimes(contestUserProblem.getWaTimes() + 1);
            }
        }
        if (hadGetContestUserProblem){
            contestUserProblemService.updateById(contestUserProblem);
        }else{
            contestUserProblemService.save(contestUserProblem);
        }
        contestProblemService.updateById(contestProblem);
        if (hadGetContestRank){
            boolean updateContestRank = contestRankService.updateById(contestRank);
            if (!updateContestRank){
                return R.error().message("更新contestRank失败");
            }
        }else{
            boolean save = contestRankService.save(contestRank);
            if (!save){
                return R.error().message("保存contestRank失败");
            }
        }
        // 加入比赛提交里
        boolean insert = contestSubmissionService.save(contestSubmission);
        if (insert){
            return judgeResult;
        }
        return R.error().message("提交失败");
    }

}
