package one.sunny.ttoj.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.commonutils.R;
import one.sunny.ttoj.entity.ProblemTag;
import one.sunny.ttoj.mapper.ProblemTagMapper;
import one.sunny.ttoj.service.ProblemTagService;
import one.sunny.ttoj.pojo.vo.oj.ProblemTagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemTagServiceImpl extends ServiceImpl<ProblemTagMapper, ProblemTag> implements ProblemTagService {
    @Autowired
    private ProblemTagMapper problemTagMapper;

    @Override
    public R getProblemTagTree() {
        ProblemTagVo problemTagVo = new ProblemTagVo();
        problemTagVo.setId(0);
        problemTagVo.setLabel("所有题目");
        List<ProblemTag> problemTags = problemTagMapper.selectList(null);
        List<ProblemTagVo> children = getProblemTagTree(new ArrayList<>(), 0, problemTags);
        problemTagVo.setChildren(children);
        return R.ok().data("problemTagTree", problemTagVo);
    }

    @Override
    public R getProblemTagsByProblemId(Long problemId) {
        List<ProblemTag> problemTags = problemTagMapper.getProblemTagsByProblemId(problemId);
        return R.ok().data("tags", problemTags);
    }

    List<ProblemTagVo> getProblemTagTree(List<ProblemTagVo> father, Integer pid, List<ProblemTag> problemTags){
        for (ProblemTag problemTag : problemTags){
            Integer parentId = problemTag.getParentId();
            if (pid.equals(parentId)){
                ProblemTagVo problemTagVo = new ProblemTagVo();
                Integer tagId = problemTag.getTagId();
                problemTagVo.setId(tagId);
                problemTagVo.setLabel(problemTag.getName());
                List<ProblemTagVo> children = new ArrayList<>();
                problemTagVo.setChildren(getProblemTagTree(children, tagId, problemTags));
                father.add(problemTagVo);
            }
        }
        return father;
    }
}
