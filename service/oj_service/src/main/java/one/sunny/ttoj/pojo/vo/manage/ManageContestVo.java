package one.sunny.ttoj.pojo.vo.manage;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import one.sunny.ttoj.entity.ContestAuthor;

import java.util.Date;
import java.util.List;

@Data
public class ManageContestVo {
    private Long id;
    private String name;
    private String description;
    private String cover;
    private Date startTime;
    private Date endTime;
    private Integer ruleType;
    private Boolean visible;
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
    /*
      -1 -> 未开始
      0 -> 比赛中
      1 -> 已结束
     */
    private Integer status;
    private List<ContestAuthor> contestAuthors;
}
