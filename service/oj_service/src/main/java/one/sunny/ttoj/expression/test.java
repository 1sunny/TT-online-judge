package one.sunny.ttoj.expression;

import one.sunny.ttoj.pojo.bo.LoginUserBo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ex")
public class test {

    public boolean hasAuthority(String authority){
        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserBo loginUserBo = (LoginUserBo) authentication.getPrincipal();
        List<String> permissions = loginUserBo.getPermissions();
        //判断用户权限集合中是否存在authority
        return permissions.contains(authority);
    }
}
