package com.yanshen.controller;
import com.yanshen.entity.SysUserRole;
import com.yanshen.service.ISysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 用户角色表 前端控制器
 * @author cyc
 * @since 2023-09-21
 */

@RestController
@Api(value ="用户角色表")
@RequestMapping("/sys-user-role")
public class SysUserRoleController {
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @GetMapping("/list")
    @ApiOperation(value = "用户角色表列表查询",notes = "list")
    @RequiresRoles("admin")
    public List<SysUserRole> list(){
        return sysUserRoleService.list();
    }

    @GetMapping("/detail")
    @ApiOperation(value = "用户角色表查看详情",notes = "SysUserRole")
    public SysUserRole getSysUserRoleById(Long id){
            return sysUserRoleService.getById(id);
    }

    @PostMapping("/save")
    @ApiOperation(value = "用户角色表新增",notes = "SysUserRole")
    public boolean saveSysUserRole(SysUserRole sysUserRole){
            return sysUserRoleService.save(sysUserRole);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "用户角色表新增/修改",notes = "SysUserRole")
    public boolean saveOrUpdate(SysUserRole sysUserRole){
            return sysUserRoleService.saveOrUpdate(sysUserRole);
    }

    @PostMapping("/update")
    @ApiOperation(value = "用户角色表修改",notes = "SysUserRole")
    public boolean update(SysUserRole sysUserRole){
        return sysUserRoleService.updateById(sysUserRole);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "用户角色表删除",notes = "SysUserRole")
    public boolean remove(Long id){
        return sysUserRoleService.removeById(id);
    }

}
