package one.sunny.ttoj.pojo.bo;

import lombok.Data;
import one.sunny.ttoj.entity.ProblemTag;

import java.util.Date;
import java.util.List;

@Data
public class ProblemWithTagsBo {
    private Long id;
    private String displayId;
    private Boolean visible;
    private String name;
    private String description;
    private String input;
    private String output;
    private String hint;
    private String level;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String languages;
    private Integer vote;
    private Integer submitNum;
    private Integer acNum;
    private String authorName;
    private String source;
    private String ioMode;
    private Date createTime;
    private Integer deleted;
    private Integer solutionNum;
    private Integer commentNum;
    private String testCaseDir;
    private String sampleCase;
    private Date updateTime;
    private Long authorId;
    private List<ProblemTag> tags;
}
