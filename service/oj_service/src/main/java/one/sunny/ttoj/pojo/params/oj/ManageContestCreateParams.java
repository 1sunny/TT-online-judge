package one.sunny.ttoj.pojo.params.oj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManageContestCreateParams implements Serializable {
    @NotNull(message = "ContestCreateParams-name参数不能为空")
    private String name;
    @NotNull(message = "ContestCreateParams-description参数不能为空")
    private String description;
    private String cover;
    @NotNull(message = "ContestCreateParams-startTime参数不能为空")
    private Date startTime;
    @NotNull(message = "ContestCreateParams-endTime参数不能为空")
    private Date endTime;
    @NotNull(message = "ContestCreateParams-ruleType参数不能为空")
    private Integer ruleType;
    @NotNull(message = "ContestCreateParams-visible参数不能为空")
    private Boolean visible;
    private String password;
    @NotNull(message = "ContestCreateParams-creatorName参数不能为空")
    private String creatorName;
    @NotNull(message = "ContestCreateParams-creatorId参数不能为空")
    private Long creatorId;
    private Integer penalty;
}
