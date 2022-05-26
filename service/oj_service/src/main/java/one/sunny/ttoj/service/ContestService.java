package one.sunny.ttoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.sunny.ttoj.entity.Contest;
import one.sunny.ttoj.pojo.params.manage.ManageContestAuthorUpdateParams;
import one.sunny.ttoj.pojo.params.manage.ManageContestAuthorUserSearchParams;
import one.sunny.ttoj.pojo.params.oj.ManageContestCreateParams;
import one.sunny.ttoj.pojo.params.oj.ContestQueryParams;
import one.sunny.ttoj.pojo.vo.manage.ManageContestAuthorVo;
import one.sunny.ttoj.pojo.vo.oj.RecentContestVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ContestService extends IService<Contest> {

    <T> T getContestById(Long id, Boolean admin);

    Map<String, Object> getContestsByCondition(ContestQueryParams contestQueryParams, Boolean admin);

    @Transactional
    Boolean changeContestVisibility(Long contestId, Boolean visible);

    @Transactional
    Boolean saveContest(ManageContestCreateParams manageContestCreateParams);

    @Transactional
    Boolean updateContest(Contest contest);

    List<RecentContestVo> getRecentContest(Integer day);

    Boolean deleteContestByContestId(Long contestId);

    List<ManageContestAuthorVo> getContestAuthorUsersBySearch(ManageContestAuthorUserSearchParams manageContestAuthorUserSearchParams);

    boolean updateContestAuthors(ManageContestAuthorUpdateParams manageContestAuthorUpdateParams);
}
