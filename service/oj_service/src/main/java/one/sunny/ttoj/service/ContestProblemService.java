package one.sunny.ttoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.sunny.ttoj.entity.ContestProblem;
import one.sunny.ttoj.entity.Problem;
import one.sunny.ttoj.pojo.params.manage.*;
import one.sunny.ttoj.pojo.vo.oj.ContestProblemVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ContestProblemService extends IService<ContestProblem> {

    @Transactional
    boolean addContestProblemByProblemId(ManageContestProblemAddParams manageContestProblemAddParams);

    Problem getProblemByContestIdAndDisplayId(Long contestId, String problemDisplayId);

    List<ContestProblemVo> getContestProblemVosByContestIdOrderByDisplayId(Long contestId, Boolean visible);

    List<ContestProblem> getContestProblemsByContestIdOrderByDisplayId(Long contestId, Boolean visible);

    Map<String, Object> getManageContestProblemsByCondition(ManageContestProblemQueryParams manageContestProblemQueryParams);

    boolean deleteContestProblemByProblemId(ManageContestProblemDeleteParams manageContestProblemDeleteParams);

    Map<String, Object> searchProblemsFromArchiveByCondition(ManageContestProblemQueryParams manageContestProblemQueryParams);

    Boolean updateContestProblem(ManageContestProblemUpdateParams manageContestProblemUpdateParams);
}
