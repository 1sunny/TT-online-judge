package one.sunny.ttoj.pojo.vo.oj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import one.sunny.ttoj.entity.ContestAuthor;

import java.util.Date;
import java.util.List;

@Data
public class ContestVo{
    private Long id;
    private String name;
    private String description;
    @ApiModelProperty(value = "封面")
    private String cover;
    private Date startTime;
    private Date endTime;
    @ApiModelProperty(value = "ACM,IOI,OI")
    private Integer ruleType;
    private Boolean passwordProtect;
    private Integer registeredNum;
    private Integer remainSecond;
    /*
      -1 -> 未开始
      0 -> 比赛中
      1 -> 已结束
     */
    private Integer status;

    private List<ContestAuthor> contestAuthors;
}
