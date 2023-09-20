package com.yanshen.controller;

import com.yanshen.common.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-15 11:14
 * @Description:
 * @Location: com.zhifou.controller
 * @Project: shiro_jwt
 */

@RestController
@RequestMapping("/sys/common")
public class CommonController {

    @RequestMapping("/tips")
    public R tips(){
        return R.fail("用户未登录!");
    }

}
