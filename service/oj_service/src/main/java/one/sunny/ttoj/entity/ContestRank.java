package one.sunny.ttoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_contest_rank")
@ApiModel(value="ContestRank对象", description="")
public class ContestRank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;
    private Long userId;
    private String username;
    private Long contestId;
    private Integer totalTime;
    private Integer acNum;
}
