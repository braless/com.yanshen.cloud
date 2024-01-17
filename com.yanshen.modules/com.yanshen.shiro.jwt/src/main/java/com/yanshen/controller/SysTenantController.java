package com.yanshen.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yanshen.common.CacheTime;
import com.yanshen.common.Result;
import com.yanshen.constants.AuthConstats;
import com.yanshen.entity.LoginUser;
import com.yanshen.entity.SysTenant;
import com.yanshen.entity.dto.SysTenantDto;
import com.yanshen.handler.exceptions.TipException;
import com.yanshen.service.ISysTenantService;
import com.yanshen.util.BcryptUtil;
import com.yanshen.util.IdWorker;
import com.yanshen.util.JwtUtil;
import com.yanshen.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 前端控制器
 *
 * @author cyc
 * @since 2023-09-19
 */

@RestController
@Api(value = "")
@RequestMapping("/sys-tenant")
public class SysTenantController {
    @Autowired
    private ISysTenantService sysTenantService;

    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    IdWorker idWorker;

    @Autowired
    RedisUtil redisUtil;

    @GetMapping(value = "/login")
    public Result tenantLogin(@RequestParam String userName, @RequestParam String password) {
        SysTenant sysTenant = sysTenantService.getOne(new LambdaQueryWrapper<SysTenant>().eq(SysTenant::getUserName, userName));
        if (!BcryptUtil.match(password,sysTenant.getPassword())){
            throw new TipException("用户密码不正确~");
        }
        String userId = sysTenant.getUserName();
        String jwtToken = jwtUtil.createJwtToken(sysTenant.getUserName(), CacheTime.JWT_EXPIRE_SECONDS);
        long expTime = jwtUtil.getExpTime(jwtToken);
        SysTenantDto dto = new SysTenantDto();
        LoginUser loginUser = new LoginUser();
        BeanUtil.copyProperties(sysTenant, loginUser);
        loginUser.setToken(jwtToken);
        redisUtil.set(AuthConstats.USER_TOKEN_PREFIX + userId, loginUser, CacheTime.JWT_REDDIS_EXPIRE, TimeUnit.SECONDS);
        dto.setToken(jwtToken);
        dto.setExpireAt(expTime + "");
        return Result.success(dto);
    }


    public void verifyAccount(SysTenant sysTenant) {
        // 判断用户状态
        if (sysTenant.getStatus() == 1) {
            throw new AuthenticationException("账号已被锁定,请联系管理员!");
        }
        if (sysTenant.getStatus() == 2) {
            throw new AuthenticationException("账号已被注销,请联系管理员!");
        }

    }


    @GetMapping("/list")
    @ApiOperation(value = "列表查询", notes = "list")
    public List<SysTenant> list() {
        return sysTenantService.list();
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查看详情", notes = "SysTenant")
    public SysTenant getSysTenantById(Long id) {
        return sysTenantService.getById(id);
    }

    @PostMapping("/register")
    @ApiOperation(value = "新增", notes = "SysTenant")
    public Result saveSysTenant(@RequestBody SysTenant sysTenant) {
//        long l = idWorker.nextId();
//        sysTenant.setId(idWorker.nextId());
        sysTenant.setPassword(BcryptUtil.encode(sysTenant.getPassword()));
        sysTenantService.save(sysTenant);
        return Result.success();

    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/修改", notes = "SysTenant")
    public boolean saveOrUpdate(SysTenant sysTenant) {
        return sysTenantService.saveOrUpdate(sysTenant);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "SysTenant")
    public boolean update(SysTenant sysTenant) {
        return sysTenantService.updateById(sysTenant);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "SysTenant")
    public Result remove(Long id) {
        sysTenantService.removeById(id);
        return  Result.success(id);
    }

}
