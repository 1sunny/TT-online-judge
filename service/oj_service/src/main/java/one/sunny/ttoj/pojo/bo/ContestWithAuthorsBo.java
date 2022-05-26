package one.sunny.ttoj.pojo.bo;

import lombok.Data;
import one.sunny.ttoj.entity.ContestAuthor;

import java.util.Date;
import java.util.List;

@Data
public class ContestWithAuthorsBo {
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
    private Date createTime;
    private Date updateTime;
    private Integer penalty;
    private List<ContestAuthor> contestAuthors;
}
