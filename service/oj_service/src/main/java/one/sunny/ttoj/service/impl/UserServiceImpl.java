package one.sunny.ttoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.baseservice.exception.TTOJException;
import one.sunny.commonutils.ErrorCode;
import one.sunny.commonutils.R;
import one.sunny.commonutils.RedisCache;
import one.sunny.ttoj.entity.Role;
import one.sunny.ttoj.entity.User;
import one.sunny.ttoj.entity.UserRole;
import one.sunny.ttoj.mapper.RoleMapper;
import one.sunny.ttoj.mapper.UserMapper;
import one.sunny.ttoj.pojo.bo.LoginUserBo;
import one.sunny.ttoj.pojo.bo.UserWithRolesBo;
import one.sunny.ttoj.pojo.params.manage.ManageUserParams;
import one.sunny.ttoj.pojo.params.oj.RankParams;
import one.sunny.ttoj.pojo.vo.manage.ManageUserVo;
import one.sunny.ttoj.pojo.vo.oj.RankVo;
import one.sunny.ttoj.service.SsoService;
import one.sunny.ttoj.service.UserRoleService;
import one.sunny.ttoj.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private SsoService ssoService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public R getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            LoginUserBo loginUserBo = (LoginUserBo) authentication.getPrincipal();
            if (loginUserBo == null){
                return R.error().message("获取用户信息失败");
            }
            return R.ok().data("loginUser", loginUserBo);
        }else{
            return R.error().message("暂未登录");
        }

    }

    @Override
    public Map<String, Object> getUserList(ManageUserParams manageUserParams) {
        Integer currentPage = manageUserParams.getCurrentPage();
        Integer pageSize = manageUserParams.getPageSize();
        if (currentPage == null || currentPage <= 0 || pageSize == null || pageSize <= 0){
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.UPDATE_ERROR.getMsg() + "分页参数异常");
        }
        String username = manageUserParams.getUsername();
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(username)){
            userWrapper.eq(User::getUsername, username);
        }
        List<UserWithRolesBo> userWithRolesBos = userMapper.selectUserWithRolesBoPage(currentPage, pageSize, username);
        Integer total = Math.toIntExact(userMapper.selectCount(null));
        Map<String, Object> usersByConditionMap = new HashMap<>();
        List<ManageUserVo> manageUserVos = copyList(userWithRolesBos);
        usersByConditionMap.put("manageUserVos", manageUserVos);
        usersByConditionMap.put("total", total);
        return usersByConditionMap;
    }

    @Override
    public void updateUserRoles(Long userId, List<String> roles) {
        LambdaQueryWrapper<Role> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.in(Role::getRoleName, roles).select(Role::getId);
        List<Role> roleList = roleMapper.selectList(roleWrapper);
        List<Long> roleIds = new ArrayList<>();
        roleList.forEach(item -> {
            roleIds.add(item.getId());
        });
        LambdaQueryWrapper<UserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(UserRole::getUserId, userId);
        userRoleService.remove(userRoleWrapper);
        for (Long roleId : roleIds){
            boolean save = userRoleService.save(new UserRole(null, userId, roleId));
            if (!save){
                throw new TTOJException(ErrorCode.INSERT_ERROR.getCode(), ErrorCode.INSERT_ERROR.getMsg() + "角色插入失败");
            }
        }
    }

    @Override
    public Map<String, Object> getRankListByCondition(RankParams rankParams) {
        Integer currentPage = rankParams.getCurrentPage();
        Integer pageSize = rankParams.getPageSize();
        if (currentPage == null || currentPage <= 0 || pageSize == null || pageSize < 1){
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg() + "分页参数异常");
        }
        String type = rankParams.getType();
        List<String> types = new ArrayList<>(Arrays.asList("rating", "ac_num", "contribution"));
        if (type == null || !types.contains(type)){
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg() + "排行类型异常");
        }
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.orderByDesc(type)
                   .select(type, "id", "username");
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userMapper.selectPage(page, userWrapper);
        List<User> records = userPage.getRecords();
        long total = userPage.getTotal();
        Map<String, Object> map = new HashMap<>();
        List<RankVo> rankVos = copyToRankList(records);
        map.put("total", total);
        map.put("rankList", rankVos);
        return map;
    }
    private <T> List<RankVo> copyToRankList(List<T> list) {
        List<RankVo> rankVos = new ManagedList<>();
        for (T t : list){
            rankVos.add(copyRanVo(t));
        }
        return rankVos;
    }

    private <T> List<ManageUserVo> copyList(List<T> list) {
        List<ManageUserVo> manageUserVos = new ManagedList<>();
        for (T t : list){
            manageUserVos.add(copy(t));
        }
        return manageUserVos;
    }

    private <T> ManageUserVo copy(T t) {
        ManageUserVo manageUserVo = new ManageUserVo();
        BeanUtils.copyProperties(t, manageUserVo);
        return manageUserVo;
    }

    private <K> RankVo copyRanVo(K t){
        RankVo rankVo = new RankVo();
        BeanUtils.copyProperties(t, rankVo);
        return rankVo;
    }
}
