package one.sunny.ttoj.expression;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import one.sunny.commonutils.Constants;
import one.sunny.ttoj.entity.ContestProblem;
import one.sunny.ttoj.entity.Problem;
import one.sunny.ttoj.pojo.bo.LoginUserBo;
import one.sunny.ttoj.pojo.params.manage.ManageProblemSaveParams;
import one.sunny.ttoj.service.ContestProblemService;
import one.sunny.ttoj.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

import static one.sunny.ttoj.expression.CommonExpresion.getLoginUserBo;
import static one.sunny.ttoj.expression.CommonExpresion.hasAuthority;

@Component("problemExpression")
public class ProblemExpression {
    @Autowired
    private ContestProblemService contestProblemService;
    @Autowired
    private ProblemService problemService;

    public boolean updateProblem(String adminProblemSaveParams){
        LoginUserBo loginUserBo = getLoginUserBo();
        if (loginUserBo == null){
            return false;
        }
        if (hasAuthority(loginUserBo, Constants.MANAGE_ALL_PROBLEM)){
            return true;
        }else if(hasAuthority(loginUserBo, Constants.MANAGE_OWN_PROBLEM)){
            // 除了超级管理员,只有作者可以修改自己创建的题目
            ManageProblemSaveParams manageProblemSaveParams = JSON.parseObject(adminProblemSaveParams, ManageProblemSaveParams.class);
            Long authorId = manageProblemSaveParams.getAuthorId();
            return loginUserBo.getUser().getId().equals(authorId);
        }
        return false;
    }

    public boolean addProblem(){
        return CommonExpresion.hasAnyAuthority(new ArrayList<>(Arrays.asList(
                Constants.MANAGE_OWN_PROBLEM,
                Constants.MANAGE_ALL_PROBLEM
        )));
    }

    public boolean deleteProblem(Long problemId){
        LoginUserBo loginUserBo = getLoginUserBo();
        if (loginUserBo == null){
            return false;
        }
        // 题目是否属于某一场比赛
        LambdaQueryWrapper<ContestProblem> contestProblemWrapper = new LambdaQueryWrapper<>();
        contestProblemWrapper.eq(ContestProblem::getProblemId, problemId);
        long count = contestProblemService.count(contestProblemWrapper);
        if (count > 0){
            return false;
        }
        if (hasAuthority(loginUserBo, Constants.MANAGE_ALL_PROBLEM)){
            return true;
        }else if(hasAuthority(loginUserBo, Constants.MANAGE_OWN_PROBLEM)){
            // 除了超级管理员,只有作者可以删除自己创建的题目
            LambdaQueryWrapper<Problem> problemWrapper = new LambdaQueryWrapper<>();
            Long userId = loginUserBo.getUser().getId();
            problemWrapper.eq(Problem::getId, problemId).eq(Problem::getAuthorId, userId);
            return problemService.count(problemWrapper) > 0;
        }
        return false;
    }
}
