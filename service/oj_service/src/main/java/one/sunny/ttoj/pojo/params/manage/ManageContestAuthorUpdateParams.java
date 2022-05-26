package one.sunny.ttoj.pojo.params.manage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageContestAuthorUpdateParams {
    @NotNull(message = "ManageContestAuthorUpdateParams-contestId参数为空")
    private Long contestId;
    private List<AuthorIdAndName> contestAuthors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthorIdAndName{
        @NotNull(message = "ManageContestAuthorUpdateParams-AuthorIdAndName-authorId参数为空")
        private Long authorId;
        @NotNull(message = "ManageContestAuthorUpdateParams-AuthorIdAndName-username参数为空")
        private String username;
    }
}
