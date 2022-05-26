package one.sunny.ttoj.pojo.params.oj;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ContestQueryParams {
    @NotNull(message = "ContestQueryParams-currentPage不能为空")
    private Integer currentPage;
    @NotNull(message = "ContestQueryParams-pageSize不能为空")
    private Integer pageSize;
    private String name;
    private Boolean visible;
    private String creatorName;
}
