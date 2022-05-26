package one.sunny.ttoj.pojo.vo.manage;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class ManageProblemVo {
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
    private Integer deleted;
    private Integer solutionNum;
    private Integer commentNum;
    private String testCaseDir;
    private String sampleCase;
    private Date createTime;
    private Date updateTime;
    private Long authorId;
}
