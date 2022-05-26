package one.sunny.ttoj.pojo.vo.manage;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ManageUserVo {
    private Long id;
    private String username;
    private String email;
    private Date createTime;
    private Date lastLogin;
    private List<String> roles;
}
