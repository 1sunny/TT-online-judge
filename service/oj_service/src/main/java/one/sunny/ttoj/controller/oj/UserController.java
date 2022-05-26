package one.sunny.ttoj.controller.oj;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import one.sunny.commonutils.R;
import one.sunny.ttoj.pojo.params.oj.RankParams;
import one.sunny.ttoj.pojo.vo.oj.RankVo;
import one.sunny.ttoj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@Api("user")
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("根据token获得当前登录用户")
    @GetMapping("currentUser")
    public R getCurrentUser(){
        return userService.getCurrentUser();
    }

    @PostMapping("rank")
    public R getRankListByCondition(@Validated @RequestBody RankParams rankParams){
        Map<String, Object> rankListByConditionMap = userService.getRankListByCondition(rankParams);
        long total = (long) rankListByConditionMap.get("total");
        List<RankVo> rankVos = (List<RankVo>) rankListByConditionMap.get("rankList");
        return R.ok().data("total", total).data("rankList", rankVos);
    }
}

