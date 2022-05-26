package one.sunny.ttoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.sunny.commonutils.R;
import one.sunny.ttoj.entity.ProblemTag;

public interface ProblemTagService extends IService<ProblemTag> {

    R getProblemTagTree();

    R getProblemTagsByProblemId(Long problemId);
}
