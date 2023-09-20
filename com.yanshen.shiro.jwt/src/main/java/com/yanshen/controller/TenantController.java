package com.yanshen.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yanshen.aspect.annotation.WebAuth;
import com.yanshen.common.CacheTime;
import com.yanshen.common.Result;
import com.yanshen.constants.AuthConstats;
import com.yanshen.entity.LoginUser;
import com.yanshen.entity.SysTenant;
import com.yanshen.entity.dto.SysTenantDto;
import com.yanshen.service.ISysTenantService;
import com.yanshen.util.JwtUtil;
import com.yanshen.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-19 16:45
 * @Description:
 * @Location: com.yanshen.controller
 * @Project: com.yanshen.cloud
 */

@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private ISysTenantService tenantService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RedisUtil redisUtil;
    @GetMapping(value = "/login")
    public Result tenantLogin(@RequestParam String userName,@RequestParam String password){

        SysTenant sysTenant = tenantService.getOne(new LambdaQueryWrapper<SysTenant>().eq(SysTenant::getUserName, userName));
        String userId=sysTenant.getUserName();
        String jwtToken = jwtUtil.createJwtToken(sysTenant.getUserName(), CacheTime.JWT_EXPIRE_SECONDS);
        long expTime = jwtUtil.getExpTime(jwtToken);
        SysTenantDto dto =new SysTenantDto();
        LoginUser loginUser =new LoginUser();
        BeanUtil.copyProperties(sysTenant,loginUser);
        loginUser.setToken(jwtToken);
        redisUtil.set(AuthConstats.USER_TOKEN_PREFIX+userId,loginUser,CacheTime.JWT_REDDIS_EXPIRE, TimeUnit.SECONDS);
        dto.setToken(jwtToken);
        dto.setExpireAt(expTime+"");
        return Result.success(dto);
    }




    @WebAuth
    @RequestMapping("/list")
    public Result getMsgList(){
        return Result.success("获取成功!");
    }
}
