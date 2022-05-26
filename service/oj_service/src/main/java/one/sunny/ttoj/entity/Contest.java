package one.sunny.ttoj.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_contest")
@ApiModel(value="Contest对象", description="")
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;
    private String name;
    private String description;
    @ApiModelProperty(value = "封面")
    private String cover;
    private Date startTime;
    private Date endTime;
    @ApiModelProperty(value = "1->ACM,2->IOI,3->OI")
    private Integer ruleType;
    private Boolean visible;
    @TableField(updateStrategy = FieldStrategy.IGNORED,
                insertStrategy = FieldStrategy.IGNORED)
    private String password;
    private Boolean passwordProtect;
    private Integer registeredNum;
    private String creatorName;
    private Long creatorId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    private Integer penalty;
}
