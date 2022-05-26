package one.sunny.ttoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_problem_tag")
public class ProblemTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;
    private String name;
    private Integer parentId;
    private Integer tagId;
}
