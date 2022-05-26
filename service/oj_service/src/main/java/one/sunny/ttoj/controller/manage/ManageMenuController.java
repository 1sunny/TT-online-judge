package one.sunny.ttoj.controller.manage;

import one.sunny.commonutils.R;
import one.sunny.ttoj.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manage/menu")
public class ManageMenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping("{userId}")
    public R getPermissionsTreeByUserId(@PathVariable("userId") Long userId){
        return R.ok();
    }
}

