package com.yanshen.controller;
import com.yanshen.entity.SysPermission;
import com.yanshen.service.ISysPermissionService;
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
 * 菜单权限表 前端控制器
 * @author cyc
 * @since 2023-09-21
 */

@RestController
@Api(value ="菜单权限表")
@RequestMapping("/sys-permission")
public class SysPermissionController {
    @Autowired
    private ISysPermissionService sysPermissionService;

    @GetMapping("/list")
    @ApiOperation(value = "菜单权限表列表查询",notes = "list")
    public List<SysPermission> list(){
        return sysPermissionService.list();
    }

    @GetMapping("/detail")
    @ApiOperation(value = "菜单权限表查看详情",notes = "SysPermission")
    public SysPermission getSysPermissionById(Long id){
            return sysPermissionService.getById(id);
    }

    @PostMapping("/save")
    @ApiOperation(value = "菜单权限表新增",notes = "SysPermission")
    public boolean saveSysPermission(SysPermission sysPermission){
            return sysPermissionService.save(sysPermission);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "菜单权限表新增/修改",notes = "SysPermission")
    public boolean saveOrUpdate(SysPermission sysPermission){
            return sysPermissionService.saveOrUpdate(sysPermission);
    }

    @PostMapping("/update")
    @ApiOperation(value = "菜单权限表修改",notes = "SysPermission")
    public boolean update(SysPermission sysPermission){
        return sysPermissionService.updateById(sysPermission);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "菜单权限表删除",notes = "SysPermission")
    public boolean remove(Long id){
        return sysPermissionService.removeById(id);
    }

}
