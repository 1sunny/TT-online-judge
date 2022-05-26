package one.sunny.ttoj.pojo.params.manage;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManageContestAuthorUserSearchParams {
    @NotNull(message = "ManageContestAuthorUserSearchParams-username参数为空")
    private String username;
    @NotNull(message = "ManageContestAuthorUserSearchParams-contestId参数为空")
    private Long contestId;
}
