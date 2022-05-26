package one.sunny.ttoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.baseservice.exception.TTOJException;
import one.sunny.commonutils.Constants;
import one.sunny.commonutils.ErrorCode;
import one.sunny.ttoj.entity.Contest;
import one.sunny.ttoj.entity.ContestAuthor;
import one.sunny.ttoj.entity.User;
import one.sunny.ttoj.mapper.ContestAuthorMapper;
import one.sunny.ttoj.mapper.ContestMapper;
import one.sunny.ttoj.pojo.bo.ContestWithAuthorsBo;
import one.sunny.ttoj.pojo.bo.LoginUserBo;
import one.sunny.ttoj.pojo.params.manage.ManageContestAuthorUpdateParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestAuthorUserSearchParams;
import one.sunny.ttoj.pojo.params.oj.ContestQueryParams;
import one.sunny.ttoj.pojo.params.oj.ManageContestCreateParams;
import one.sunny.ttoj.pojo.vo.manage.ManageContestAuthorVo;
import one.sunny.ttoj.pojo.vo.manage.ManageContestVo;
import one.sunny.ttoj.pojo.vo.oj.ContestVo;
import one.sunny.ttoj.pojo.vo.oj.RecentContestVo;
import one.sunny.ttoj.service.ContestService;
import one.sunny.ttoj.service.UserService;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest> implements ContestService {
    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private ContestAuthorMapper contestAuthorMapper;
    @Autowired
    private UserService userService;

    public ArrayList<Integer> getContestStatus(DateTime startTime, DateTime endTime) {
        Integer remainSecond, status = 0;
        DateTime now = new DateTime();
        /*
             remainSecond
             0 -> 比赛中
             -1 -> 比赛结束
         */
        if (now.isBefore(startTime)) {
            status = -1;
            remainSecond = Seconds.secondsBetween(now, startTime).getSeconds();
        } else if (now.isAfter(endTime)) {
            status = 1;
            remainSecond = -1;
        } else {
            remainSecond = Seconds.secondsBetween(now, endTime).getSeconds();
        }
        return new ArrayList<>(Arrays.asList(remainSecond, status));
    }

    public <T> ContestVo copy(T contest) {
        ContestVo contestVo = new ContestVo();
        BeanUtils.copyProperties(contest, contestVo);
        ArrayList<Integer> contestStatus = getContestStatus(new DateTime(contestVo.getStartTime()), new DateTime(contestVo.getEndTime()));
        contestVo.setRemainSecond(contestStatus.get(0));
        contestVo.setStatus(contestStatus.get(1));
        return contestVo;
    }

    public <T> List<ContestVo> copyList(List<T> contests) {
        List<ContestVo> contestVos = new ArrayList<>();
        for (T contest : contests) {
            ContestVo contestVo = copy(contest);
            contestVos.add(contestVo);
        }
        return contestVos;
    }

    public <T> ManageContestVo adminCopy(T contest) {
        ManageContestVo manageContestVo = new ManageContestVo();
        BeanUtils.copyProperties(contest, manageContestVo);
        ArrayList<Integer> contestStatus = getContestStatus(new DateTime(manageContestVo.getStartTime()), new DateTime(manageContestVo.getEndTime()));
        manageContestVo.setStatus(contestStatus.get(1));
        return manageContestVo;
    }

    public <T> List<ManageContestVo> adminCopyList(List<T> contests) {
        List<ManageContestVo> manageContestVos = new ArrayList<>();
        for (T contest : contests) {
            ManageContestVo manageContestVo = adminCopy(contest);
            manageContestVos.add(manageContestVo);
        }
        return manageContestVos;
    }

    /**
     * admin = true, 需要是比赛作者或者有管理所有比赛的权限
     * admin = false, 则必须可见
     * @param id
     * @param admin
     * @param <T>
     * @return
     */
    @Override
    public <T> T getContestById(Long id, Boolean admin) {
        ContestWithAuthorsBo contestWithAuthorsBo = contestMapper.getContestWithAuthorsBoByContestId(id, admin);
        Assert.notNull(contestWithAuthorsBo, "比赛不存在");
        if (admin) {
            ManageContestVo manageContestVo = adminCopy(contestWithAuthorsBo);
            return (T) manageContestVo;
        } else {
            ContestVo contestVo = copy(contestWithAuthorsBo);
            return (T) contestVo;
        }
    }

    @Override
    public Map<String, Object> getContestsByCondition(ContestQueryParams contestQueryParams, Boolean admin) {
        /**
         * 不是 admin只能看 visible = true 的
         */
        if (!admin){
            contestQueryParams.setVisible(true);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (!(authentication instanceof AnonymousAuthenticationToken) && admin) {
            LoginUserBo loginUserBo = (LoginUserBo) authentication.getPrincipal();
            List<String> permissions = loginUserBo.getPermissions();
            // 如果没有管理所有比赛权限, 就返回是比赛出题人的比赛
            if (admin && !permissions.contains(Constants.MANAGE_ALL_CONTEST)){
                userId = loginUserBo.getUser().getId();
            }
        }
        List<ContestWithAuthorsBo> contestWithAuthorsBos = contestMapper.getContestWithAuthorsBoByCondition(contestQueryParams, userId);
        LambdaQueryWrapper<Contest> contestWrapper = new LambdaQueryWrapper<>();
        String creatorName = contestQueryParams.getCreatorName();
        String name = contestQueryParams.getName();
        Boolean visible = contestQueryParams.getVisible();
        contestWrapper.like(!StringUtils.isEmpty(name), Contest::getName, name)
                .like(!StringUtils.isEmpty(creatorName), Contest::getCreatorName, creatorName)
                .eq(visible != null, Contest::getVisible, visible);
        long total = contestMapper.selectCount(contestWrapper);
        Map<String, Object> contestsByConditionMap = new HashMap<>();
        contestsByConditionMap.put("total", total);
        if (admin) {
            List<ManageContestVo> manageContestVos = adminCopyList(contestWithAuthorsBos);
            contestsByConditionMap.put("contests", manageContestVos);
            return contestsByConditionMap;
        } else {
            List<ContestVo> contestVos = copyList(contestWithAuthorsBos);
            contestsByConditionMap.put("contests", contestVos);
            return contestsByConditionMap;
        }
    }

    @Override
    public Boolean changeContestVisibility(Long contestId, Boolean visible) {
        LambdaUpdateWrapper<Contest> contestUpdateWrapper = new LambdaUpdateWrapper<>();
        Contest contest = new Contest();
        contest.setId(contestId);
        contest.setVisible(visible);
        contestUpdateWrapper.eq(Contest::getId, contestId).set(Contest::getVisible, visible);
        int update = contestMapper.update(contest, contestUpdateWrapper);
        if (update > 0) {
            return true;
        }
        throw new TTOJException(ErrorCode.UPDATE_ERROR.getCode(), "更新失败");
    }

    @Override
    public Boolean saveContest(ManageContestCreateParams manageContestCreateParams) {
        Contest contest = new Contest();
        BeanUtils.copyProperties(manageContestCreateParams, contest);
        String contestName = contest.getName().trim();
        LambdaQueryWrapper<Contest> contestWrapper = new LambdaQueryWrapper<>();
        contestWrapper.eq(Contest::getName, contestName);
        Long count = contestMapper.selectCount(contestWrapper);
        if (count > 0) {
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg() + "比赛名字已经存在");
        }
        contest.setName(contestName);
        if (!StringUtils.isEmpty(contest.getPassword())) {
            contest.setPasswordProtect(true);
        } else {
            contest.setPassword(null);
        }
        contest.setCover(Constants.DefaultContestCover);
        contest.setRegisteredNum(0);
        int insert = contestMapper.insert(contest);
        if (insert > 0) {
            // 将比赛创建者加入比赛作者列表里
            String creatorName = manageContestCreateParams.getCreatorName();
            Long creatorId = manageContestCreateParams.getCreatorId();
            ContestAuthor contestAuthor = new ContestAuthor();
            contestAuthor.setUserId(creatorId);
            contestAuthor.setUsername(creatorName);
            contestAuthor.setContestId(contest.getId());
            contestAuthorMapper.insert(contestAuthor);
            return true;
        }
        throw new TTOJException(ErrorCode.INSERT_ERROR.getCode(), "比赛保存失败");
    }

    @Override
    public Boolean updateContest(Contest contest) {
        String contestName = contest.getName().trim();
        LambdaQueryWrapper<Contest> contestWrapper = new LambdaQueryWrapper<>();
        contestWrapper.eq(Contest::getName, contestName);
        Long count = contestMapper.selectCount(contestWrapper);
        if (count > 0) {
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg() + "比赛名字已经存在");
        }
        contest.setName(contestName);
        if (!StringUtils.isEmpty(contest.getPassword())) {
            contest.setPasswordProtect(true);
        } else {
            contest.setPassword(null);
        }
        /**
         * updateById方法在插入时，会根据实体类的每个属性进行非空判断，只有非空的属性所对应的字段才会出现在SQL语句中。
         * updateAllColumnById方法在插入时，不管属性是否为空，属性所对应的字段都会出现在
         */
        int update = contestMapper.updateById(contest);
        if (update > 0) {
            return true;
        }
        throw new TTOJException(ErrorCode.UPDATE_ERROR.getCode(), "比赛更新失败");
    }

    @Override
    public List<RecentContestVo> getRecentContest(Integer day) {
        LambdaQueryWrapper<Contest> contestWrapper = new LambdaQueryWrapper<>();
        DateTime now = new DateTime();
        DateTime daysAfterNow = now.minusDays(-day);
        /*
        1. 已经开始,还没结束
        2. day天之内开始
         */
        contestWrapper.eq(Contest::getVisible, true).and(
                x -> x.le(Contest::getStartTime, now.toDate()).gt(Contest::getEndTime, now.toDate())
                .or()
                .le(Contest::getStartTime, daysAfterNow.toDate())
                .gt(Contest::getStartTime, now.toDate())
        )
                .select(Contest::getId, Contest::getName, Contest::getStartTime, Contest::getEndTime);
        List<Contest> contests = contestMapper.selectList(contestWrapper);
        List<RecentContestVo> recentContestVos = new ArrayList<>();
        for (Contest contest : contests) {
            ArrayList<Integer> contestStatus = getContestStatus(new DateTime(contest.getStartTime()), new DateTime(contest.getEndTime()));
            recentContestVos.add(new RecentContestVo(contest.getId(), contest.getName(), contestStatus.get(0), contestStatus.get(1)));
        }
        return recentContestVos;
    }

    @Override
    public Boolean deleteContestByContestId(Long contestId) {
        if (contestId == null) {
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg() + "contestId为空");
        }
        int delete = contestMapper.deleteById(contestId);
        if (delete > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<ManageContestAuthorVo> getContestAuthorUsersBySearch(ManageContestAuthorUserSearchParams manageContestAuthorUserSearchParams) {
        Long contestId = manageContestAuthorUserSearchParams.getContestId();
        String username = manageContestAuthorUserSearchParams.getUsername();
        LambdaQueryWrapper<ContestAuthor> contestAuthorWrapper = new LambdaQueryWrapper<>();
        contestAuthorWrapper.eq(ContestAuthor::getContestId, contestId).select(ContestAuthor::getUserId);
        List<ContestAuthor> contestAuthors = contestAuthorMapper.selectList(contestAuthorWrapper);
        List<Long> contestAuthorsIds = new ArrayList<>();
        List<ManageContestAuthorVo> manageContestAuthorVos = new ArrayList<>();
        contestAuthors.forEach((item) -> {
            contestAuthorsIds.add(item.getUserId());
        });
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.like(User::getUsername, username)
                .notIn(contestAuthorsIds.size() > 0, User::getId, contestAuthorsIds)
                .select(User::getId, User::getUsername);
        List<User> contestAuthorCandidate = userService.list(userWrapper);
        contestAuthorCandidate.forEach((item) -> {
            manageContestAuthorVos.add(new ManageContestAuthorVo(item.getId(), item.getUsername()));
        });
        return manageContestAuthorVos;
    }

    @Override
    public boolean updateContestAuthors(ManageContestAuthorUpdateParams manageContestAuthorUpdateParams) {
        Long contestId = manageContestAuthorUpdateParams.getContestId();
        List<ManageContestAuthorUpdateParams.AuthorIdAndName> authors = manageContestAuthorUpdateParams.getContestAuthors();

        LambdaQueryWrapper<ContestAuthor> contestAuthorWrapper = new LambdaQueryWrapper<>();
        contestAuthorWrapper.eq(ContestAuthor::getContestId, contestId)
                .select(ContestAuthor::getUserId);
        List<ContestAuthor> contestAuthors = contestAuthorMapper.selectList(contestAuthorWrapper);
        for (ManageContestAuthorUpdateParams.AuthorIdAndName authorIdAndName : authors) {
            Long authorId = authorIdAndName.getAuthorId();
            if (!contestAuthors.stream().anyMatch(item -> authorId.equals(item.getUserId()))) {
                contestAuthorMapper.insert(new ContestAuthor(null, authorId, authorIdAndName.getUsername(), contestId));
            }
        }
        for (ContestAuthor contestAuthor : contestAuthors) {
            if (!authors.stream().anyMatch(item -> contestAuthor.getUserId().equals(item.getAuthorId()))) {
                contestAuthorMapper.delete(new LambdaQueryWrapper<ContestAuthor>()
                        .eq(ContestAuthor::getContestId, contestId)
                        .eq(ContestAuthor::getUserId, contestAuthor.getUserId())
                );
            }
        }
        return false;
    }
}
