package one.sunny.ttoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import one.sunny.ttoj.entity.ProblemTag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemTagMapper extends BaseMapper<ProblemTag> {

    List<ProblemTag> getProblemTagsByProblemId(Long problemId);

}
