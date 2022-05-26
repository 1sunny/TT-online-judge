package one.sunny.ttoj.pojo.vo.oj;


import lombok.Data;

import java.util.List;

@Data
public class ProblemTagVo {
    private String label;
    private List<ProblemTagVo> children;
    private Integer id;
}
