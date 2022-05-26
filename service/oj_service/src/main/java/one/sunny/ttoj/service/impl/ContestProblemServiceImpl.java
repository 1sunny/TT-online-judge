package one.sunny.ttoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.ttoj.entity.ContestProblem;
import one.sunny.ttoj.entity.ContestSubmission;
import one.sunny.ttoj.entity.ContestUserProblem;
import one.sunny.ttoj.entity.Problem;
import one.sunny.ttoj.mapper.ContestProblemMapper;
import one.sunny.ttoj.mapper.ContestSubmissionMapper;
import one.sunny.ttoj.mapper.ProblemMapper;
import one.sunny.ttoj.pojo.bo.LoginUserBo;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemAddParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemDeleteParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemQueryParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemUpdateParams;
import one.sunny.ttoj.pojo.vo.manage.ManageContestProblemVo;
import one.sunny.ttoj.pojo.vo.manage.ManageSearchProblemVo;
import one.sunny.ttoj.pojo.vo.oj.ContestProblemVo;
import one.sunny.ttoj.service.ContestProblemService;
import one.sunny.ttoj.service.ContestUserProblemService;
import one.sunny.ttoj.service.ProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContestProblemServiceImpl extends ServiceImpl<ContestProblemMapper, ContestProblem> implements ContestProblemService {
    @Autowired
    private ContestProblemMapper contestProblemMapper;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private ContestUserProblemService contestUserProblemService;
    @Autowired
    private ContestSubmissionMapper contestSubmissionMapper;

    @Override
    public Problem getProblemByContestIdAndDisplayId(Long contestId, String problemDisplayId) {
        Problem problem = contestProblemMapper.getProblemByContestIdAndDisplayId(contestId, problemDisplayId);
        return problem;
    }

    @Override
    public List<ContestProblemVo> getContestProblemVosByContestIdOrderByDisplayId(Long contestId, Boolean visible) {

        LambdaQueryWrapper<ContestProblem> contestProblemWrapper = new LambdaQueryWrapper<>();
        contestProblemWrapper.eq(ContestProblem::getContestId, contestId)
                .orderByAsc(ContestProblem::getProblemDisplayId);
        if (visible != null){
            contestProblemWrapper.eq(ContestProblem::getVisible, true);
        }
        List<ContestProblem> contestProblems = contestProblemMapper.selectList(contestProblemWrapper);
        List<ContestProblemVo> contestProblemVos = new ArrayList<>();
        contestProblems.forEach((item) -> {
            ContestProblemVo contestProblemVo = new ContestProblemVo();
            BeanUtils.copyProperties(item, contestProblemVo);
            contestProblemVos.add(contestProblemVo);
        });
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //如果没有用户信息, 不查询题目通过情况
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            LoginUserBo loginUserBo = (LoginUserBo) authentication.getPrincipal();
            String userId = loginUserBo.getUser().getId().toString();
            for (ContestProblemVo contestProblemVo : contestProblemVos) {
                Long problemId = contestProblemVo.getProblemId();
                LambdaQueryWrapper<ContestUserProblem> contestUserProblemWrapper = new LambdaQueryWrapper<>();
                contestUserProblemWrapper.eq(ContestUserProblem::getUserId, userId)
                        .eq(ContestUserProblem::getProblemId, problemId);
                if (contestUserProblemService.count(contestUserProblemWrapper) > 0) {
                    contestProblemVo.setAlreadyPassed(true);
                }
            }
        }
        return contestProblemVos;
    }

    @Override
    public List<ContestProblem> getContestProblemsByContestIdOrderByDisplayId(Long contestId, Boolean visible) {

        LambdaQueryWrapper<ContestProblem> contestProblemWrapper = new LambdaQueryWrapper<>();
        contestProblemWrapper.eq(ContestProblem::getContestId, contestId)
                .orderByAsc(ContestProblem::getProblemDisplayId);
        if (visible != null){
            contestProblemWrapper.eq(ContestProblem::getVisible, true);
        }
        List<ContestProblem> contestProblems = contestProblemMapper.selectList(contestProblemWrapper);
        return contestProblems;
    }

    @Override
    public Map<String, Object> getManageContestProblemsByCondition(ManageContestProblemQueryParams manageContestProblemQueryParams) {
        Long contestId = manageContestProblemQueryParams.getContestId();
        String authorName = manageContestProblemQueryParams.getAuthorName();
        Integer currentPage = manageContestProblemQueryParams.getCurrentPage();
        String problemName = manageContestProblemQueryParams.getProblemName();
        Integer pageSize = manageContestProblemQueryParams.getPageSize();
        Boolean visible = manageContestProblemQueryParams.getVisible();
        LambdaQueryWrapper<ContestProblem> contestProblemWrapper = new LambdaQueryWrapper<>();
        contestProblemWrapper.eq(ContestProblem::getContestId, contestId);
        if (!StringUtils.isEmpty(authorName)){
            contestProblemWrapper.like(ContestProblem::getAuthorName, authorName);
        }
        if (!StringUtils.isEmpty(problemName)){
            contestProblemWrapper.like(ContestProblem::getProblemName, problemName);
        }
        if (visible != null){
            contestProblemWrapper.eq(ContestProblem::getVisible, visible);
        }

        Page<ContestProblem> page = new Page<>(currentPage, pageSize);
        Page<ContestProblem> contestProblemPage = contestProblemMapper.selectPage(page, contestProblemWrapper);
        List<ContestProblem> contestProblems = contestProblemPage.getRecords();
        List<ManageContestProblemVo> manageContestProblemVos = copyContestProblemListToManageContestProblemVoList(contestProblems);
        Integer total = Math.toIntExact(contestProblemPage.getTotal());
        Map<String, Object> contestProblemPageMap = new HashMap<>();
        contestProblemPageMap.put("manageContestProblemVos", manageContestProblemVos);
        contestProblemPageMap.put("total", total);
        return contestProblemPageMap;
    }

    @Override
    public boolean deleteContestProblemByProblemId(ManageContestProblemDeleteParams manageContestProblemDeleteParams) {
        Long contestId = manageContestProblemDeleteParams.getContestId();
        Long problemId = manageContestProblemDeleteParams.getProblemId();
        LambdaQueryWrapper<ContestProblem> contestProblemWrapper = new LambdaQueryWrapper<>();
        contestProblemWrapper.eq(ContestProblem::getContestId, contestId)
                .eq(ContestProblem::getProblemId, problemId);
        int delete = contestProblemMapper.delete(contestProblemWrapper);
        return delete > 0;
    }

    @Override
    public Map<String, Object> searchProblemsFromArchiveByCondition(ManageContestProblemQueryParams manageContestProblemQueryParams) {
        Long contestId = manageContestProblemQueryParams.getContestId();
        Integer currentPage = manageContestProblemQueryParams.getCurrentPage();
        Integer pageSize = manageContestProblemQueryParams.getPageSize();

        LambdaQueryWrapper<ContestProblem> contestProblemWrapper = new LambdaQueryWrapper<>();
        contestProblemWrapper.eq(ContestProblem::getContestId, contestId).select(ContestProblem::getProblemId);
        List<ContestProblem> contestProblems = contestProblemMapper.selectList(contestProblemWrapper);
        List<Long> problemIds = new ArrayList<>();
        contestProblems.forEach((item) -> {
            problemIds.add(item.getProblemId());
        });
        LambdaQueryWrapper<Problem> problemWrapper = new LambdaQueryWrapper<>();
        Page<Problem> page = new Page<>(currentPage, pageSize);
        problemWrapper.notIn(problemIds.size() > 0, Problem::getId, problemIds).select(Problem::getId, Problem::getDisplayId, Problem::getName, Problem::getAuthorName, Problem::getVisible);
        problemMapper.selectPage(page, problemWrapper);
        List<Problem> records = page.getRecords();
        long total = page.getTotal();
        List<ManageSearchProblemVo> manageSearchProblemVos = new ArrayList<>();
        records.forEach((item) -> {
            manageSearchProblemVos.add(new ManageSearchProblemVo(
                    item.getId(),
                    item.getDisplayId(),
                    item.getName(),
                    item.getAuthorName(),
                    item.getVisible()
                    ));
        });
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("manageSearchProblemVos", manageSearchProblemVos);
        return map;
    }

    @Override
    public Boolean updateContestProblem(ManageContestProblemUpdateParams manageContestProblemUpdateParams) {
        Long contestId = manageContestProblemUpdateParams.getContestId();
        Long problemId = manageContestProblemUpdateParams.getProblemId();
        String problemDisplayId = manageContestProblemUpdateParams.getProblemDisplayId();
        Boolean visible = manageContestProblemUpdateParams.getVisible();

        // 比赛是否存在
        LambdaQueryWrapper<ContestProblem> contestProblemWrapper = new LambdaQueryWrapper<>();
        contestProblemWrapper.eq(ContestProblem::getContestId, contestId)
                .eq(ContestProblem::getProblemId, problemId);
        Long count = contestProblemMapper.selectCount(contestProblemWrapper);
        Assert.state(count > 0, "比赛不存在");
        // 修改 contest problem
        LambdaUpdateWrapper<ContestProblem> contestProblemUpdateWrapper = new LambdaUpdateWrapper<>();
        contestProblemUpdateWrapper.eq(ContestProblem::getContestId, contestId)
                .eq(ContestProblem::getProblemId, problemId)
                .set(problemDisplayId != null, ContestProblem::getProblemDisplayId, problemDisplayId)
                .set(visible != null, ContestProblem::getVisible, visible);
        int update = contestProblemMapper.update(null, contestProblemUpdateWrapper);
        Assert.state(update > 0, "update失败");
        if (problemDisplayId != null){
            // 修改比赛提交中的 problemDisplayId
            LambdaUpdateWrapper<ContestSubmission> contestSubmissionUpdateWrapper = new LambdaUpdateWrapper<>();
            contestSubmissionUpdateWrapper.eq(ContestSubmission::getContestId, contestId)
                    .eq(ContestSubmission::getProblemId, problemId)
                    .set(ContestSubmission::getProblemDisplayId, problemDisplayId);
            int update1 = contestSubmissionMapper.update(null, contestSubmissionUpdateWrapper);
            Assert.state(update1 > 0, "update失败");
        }
        return true;
    }

    private List<ManageContestProblemVo> copyContestProblemListToManageContestProblemVoList(List<ContestProblem> contestProblems) {
        List<ManageContestProblemVo> list = new ArrayList<>();
        for (ContestProblem contestProblem : contestProblems){
            list.add(copy(contestProblem));
        }
        return list;
    }

    private ManageContestProblemVo copy(ContestProblem contestProblem) {
        ManageContestProblemVo manageContestProblemVo = new ManageContestProblemVo();
        BeanUtils.copyProperties(contestProblem, manageContestProblemVo);
        return manageContestProblemVo;
    }

    @Override
    public boolean addContestProblemByProblemId(ManageContestProblemAddParams manageContestProblemAddParams) {
        ContestProblem contestProblem = new ContestProblem();
        BeanUtils.copyProperties(manageContestProblemAddParams, contestProblem);
        contestProblem.setSubmitNum(0);
        contestProblem.setAcNum(0);
        contestProblem.setVisible(false);
        int insert = contestProblemMapper.insert(contestProblem);
        Assert.state(insert > 0, "insert失败");
        return true;
    }
}
