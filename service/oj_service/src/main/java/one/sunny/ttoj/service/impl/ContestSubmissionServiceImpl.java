package one.sunny.ttoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.commonutils.Constants;
import one.sunny.ttoj.entity.ContestAuthor;
import one.sunny.ttoj.entity.ContestSubmission;
import one.sunny.ttoj.mapper.ContestSubmissionMapper;
import one.sunny.ttoj.pojo.bo.LoginUserBo;
import one.sunny.ttoj.pojo.params.oj.ContestSubmissionParams;
import one.sunny.ttoj.pojo.vo.oj.ContestSubmissionVo;
import one.sunny.ttoj.service.ContestAuthorService;
import one.sunny.ttoj.service.ContestSubmissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContestSubmissionServiceImpl extends ServiceImpl<ContestSubmissionMapper, ContestSubmission> implements ContestSubmissionService {
    @Autowired
    private ContestSubmissionMapper contestSubmissionMapper;
    @Autowired
    private ContestAuthorService contestAuthorService;

    public List<ContestSubmissionVo> copyList(List<ContestSubmission> contestSubmissions){
        List<ContestSubmissionVo> contestSubmissionVos = new ArrayList<>();
        for (ContestSubmission contestSubmission : contestSubmissions){
            contestSubmissionVos.add(copy(contestSubmission));
        }
        return contestSubmissionVos;
    }
    public ContestSubmissionVo copy(ContestSubmission contestSubmission){
        ContestSubmissionVo contestSubmissionVo = new ContestSubmissionVo();
        BeanUtils.copyProperties(contestSubmission, contestSubmissionVo);
        contestSubmissionVo.setMemoryUse(contestSubmissionVo.getMemoryUse() / 1024);
        return contestSubmissionVo;
    }

    @Override
    public Map<String, Object> getContestSubmission(ContestSubmissionParams contestSubmissionParams) {
        Long contestId = contestSubmissionParams.getContestId();
        Integer currentPage = contestSubmissionParams.getCurrentPage();
        Integer pageSize = contestSubmissionParams.getPageSize();
        String language = contestSubmissionParams.getLanguage();
        String result = contestSubmissionParams.getResult();
        String problemDisplayId = contestSubmissionParams.getProblemDisplayId();
        String username = contestSubmissionParams.getUsername();
        LambdaQueryWrapper<ContestSubmission> contestSubmissionWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(language)){
            contestSubmissionWrapper.eq(ContestSubmission::getLanguage, language);
        }
        if (!StringUtils.isEmpty(result)){
            contestSubmissionWrapper.eq(ContestSubmission::getLanguage, language);
        }
        if (!StringUtils.isEmpty(problemDisplayId)){
            contestSubmissionWrapper.eq(ContestSubmission::getProblemDisplayId, problemDisplayId);
        }
        if (!StringUtils.isEmpty(username)){
            contestSubmissionWrapper.eq(ContestSubmission::getUsername, username);
        }
        contestSubmissionWrapper.orderByDesc(ContestSubmission::getSubmitTime);

        // 匿名用户和普通用户不会看到比赛出题人和管理员的代码, 管理者本身可以看见
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            Object principal = authentication.getPrincipal();
            LoginUserBo loginUserBo = (LoginUserBo) authentication.getPrincipal();
            LambdaQueryWrapper<ContestAuthor> contestAuthorWrapper = new LambdaQueryWrapper<>();
            contestAuthorWrapper.eq(ContestAuthor::getContestId, contestId)
                    .select(ContestAuthor::getUserId);
            List<ContestAuthor> list = contestAuthorService.list(contestAuthorWrapper);
            List<Long> contestAuthorIds = new ArrayList<>();
            list.forEach((item) -> {
                contestAuthorIds.add(item.getUserId());
            });
            Long userId = loginUserBo.getUser().getId();
            // 不是比赛出题人并且不是超级管理员
            if (!contestAuthorIds.contains(userId) && !loginUserBo.getPermissions().contains(Constants.MANAGE_ALL_CONTEST)){
                contestSubmissionWrapper.notIn(contestAuthorIds.size() > 0, ContestSubmission::getUserId, contestAuthorIds);
            }
        }
        Page<ContestSubmission> page = new Page<>(currentPage, pageSize);
        contestSubmissionMapper.selectPage(page, contestSubmissionWrapper);
        List<ContestSubmission> contestSubmissions = page.getRecords();
        List<ContestSubmissionVo> contestSubmissionVos = copyList(contestSubmissions);
        long total = page.getTotal();
        Map<String, Object> contestSubmissionsByConditionMap = new HashMap<>();
        contestSubmissionsByConditionMap.put("contestSubmissions", contestSubmissionVos);
        contestSubmissionsByConditionMap.put("total", total);
        return contestSubmissionsByConditionMap;
    }
}
