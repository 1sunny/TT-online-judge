package one.sunny.ttoj.expression;

import one.sunny.commonutils.Constants;
import one.sunny.ttoj.entity.Contest;
import one.sunny.ttoj.entity.ContestAuthor;
import one.sunny.ttoj.mapper.ContestMapper;
import one.sunny.ttoj.pojo.bo.ContestWithAuthorsBo;
import one.sunny.ttoj.pojo.bo.LoginUserBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static one.sunny.ttoj.expression.CommonExpresion.getLoginUserBo;
import static one.sunny.ttoj.expression.CommonExpresion.hasAuthority;

@Component("contestExpression")
public class ContestExpression {
    @Autowired
    private ContestMapper contestMapper;

    public boolean hasManageContestPermissionOrIsContestAuthor(Long contestId, ContestWithAuthorsBo contestWithAuthorsBo){
        LoginUserBo loginUserBo = getLoginUserBo();
        Assert.notNull(loginUserBo, "请登录后进行操作");
        Assert.notNull(contestWithAuthorsBo, "比赛信息不存在");
        // 拥有管理所有比赛的权力
        if (hasAuthority(loginUserBo, Constants.MANAGE_ALL_CONTEST)){
            return true;
        }
        // 比赛出题人之一
        Long userId = loginUserBo.getUser().getId();
        List<ContestAuthor> contestAuthors = contestWithAuthorsBo.getContestAuthors();
        for (ContestAuthor contestAuthor : contestAuthors){
            if (contestAuthor.getUserId().equals(userId)){
                return true;
            }
        }
        return false;
    }


    public boolean deleteContest(Long contestId){
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(contestId,  true);
        return hasManageContestPermissionOrIsContestAuthor(contestId, contestWithAuthorsBo);
    }

    public boolean createContest(){
        return CommonExpresion.hasAnyAuthority(new ArrayList<>(Arrays.asList(Constants.MANAGE_ALL_CONTEST, Constants.MANAGE_OWN_CONTEST)));
    }

    public boolean updateContest(Contest contest){
        Long contestId = contest.getId();
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(contestId,  true);
        return hasManageContestPermissionOrIsContestAuthor(contestId, contestWithAuthorsBo);
    }

    public boolean updateContest(Long contestId){
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(contestId, true);
        return hasManageContestPermissionOrIsContestAuthor(contestId, contestWithAuthorsBo);
    }
}
