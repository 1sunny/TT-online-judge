package one.sunny.ttoj.service;


import one.sunny.commonutils.R;
import one.sunny.ttoj.pojo.params.oj.ContestSubmitParams;
import one.sunny.ttoj.pojo.params.oj.JudgeParams;
import one.sunny.ttoj.pojo.params.oj.ArchiveJudgeParams;
import org.springframework.transaction.annotation.Transactional;

public interface JudgeService {
    R judge(JudgeParams judgeParams);

    @Transactional
    R problemArchiveJudge(ArchiveJudgeParams archiveJudgeParams);

    @Transactional
    R contestSubmit(ContestSubmitParams contestSubmitParams);
}
