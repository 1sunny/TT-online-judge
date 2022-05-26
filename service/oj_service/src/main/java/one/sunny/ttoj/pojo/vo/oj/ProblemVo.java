package one.sunny.ttoj.pojo.vo.oj;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import one.sunny.ttoj.entity.ProblemTag;

import java.util.List;

@Data
public class ProblemVo {
    private Long id;
    private String displayId;
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
    private Long authorId;
    private List<ProblemTag> tags;
    private Boolean alreadyPassed;
}
