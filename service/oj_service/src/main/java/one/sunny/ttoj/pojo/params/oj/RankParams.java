package one.sunny.ttoj.pojo.params.oj;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RankParams {
    @NotNull(message = "RankParams-currentPage不能为空")
    private Integer currentPage;
    @NotNull(message = "RankParams-pageSize不能为空")
    private Integer pageSize;
    @NotNull(message = "RankParams-type不能为空")
    private String type;
}
