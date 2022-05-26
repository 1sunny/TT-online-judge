package one.sunny.ttoj.expression;

import one.sunny.ttoj.mapper.ContestMapper;
import one.sunny.ttoj.mapper.ContestProblemMapper;
import one.sunny.ttoj.pojo.bo.ContestWithAuthorsBo;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemAddParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemDeleteParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemQueryParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestProblemUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

@Component("contestProblemExpression")
public class ContestProblemExpression {
    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private ContestExpression contestExpression;

    @Autowired
    private ContestProblemMapper contestProblemMapper;

    public boolean viewContestProblem(Long contestId){
        // 比赛开始后可以查看题目
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(contestId, true);
        if (contestWithAuthorsBo == null || new Date().after(contestWithAuthorsBo.getStartTime())){
            return true;
        }
        // 属于比赛管理员或出题人
        return contestExpression.hasManageContestPermissionOrIsContestAuthor(contestId, contestWithAuthorsBo);
    }

    public boolean viewManageContestProblem(ManageContestProblemQueryParams manageContestProblemQueryParams){
        Long contestId = manageContestProblemQueryParams.getContestId();
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(contestId, true);
        return contestExpression.hasManageContestPermissionOrIsContestAuthor(contestId, contestWithAuthorsBo);
    }

    public boolean addContestProblem(ManageContestProblemAddParams manageContestProblemAddParams){
        Long contestId = manageContestProblemAddParams.getContestId();
        // 比赛结束后不能添加题目
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(contestId, true);
        if (contestWithAuthorsBo == null || new Date().after(contestWithAuthorsBo.getEndTime())){
            return false;
        }
        return contestExpression.hasManageContestPermissionOrIsContestAuthor(contestId, contestWithAuthorsBo);
    }

    public boolean deleteContestProblem(ManageContestProblemDeleteParams manageContestProblemDeleteParams){
        Long contestId = manageContestProblemDeleteParams.getContestId();
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(contestId, true);
        // 比赛开始后不能删除题目
        if (contestWithAuthorsBo == null || new Date().after(contestWithAuthorsBo.getStartTime())){
            return false;
        }
        return contestExpression.hasManageContestPermissionOrIsContestAuthor(contestId, contestWithAuthorsBo);
    }

    public boolean updateContestProblem(ManageContestProblemUpdateParams manageContestProblemUpdateParams){
        Long contestId = manageContestProblemUpdateParams.getContestId();
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(contestId, true);
        // 比赛结束后不能修改题目
        if (contestWithAuthorsBo == null || new Date().after(contestWithAuthorsBo.getEndTime())){
            Assert.isTrue(false, "test");
            return false;
        }
        return contestExpression.hasManageContestPermissionOrIsContestAuthor(contestId, contestWithAuthorsBo);
    }
}
