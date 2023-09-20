package com.yanshen.controller;
import com.yanshen.entity.SysTenant;
import com.yanshen.service.ISysTenantService;
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
 *  前端控制器
 * @author cyc
 * @since 2023-09-19
 */

@RestController
@Api(value ="")
@RequestMapping("/sys-tenant")
public class SysTenantController {
    @Autowired
    private ISysTenantService sysTenantService;

    @GetMapping("/list")
    @ApiOperation(value = "列表查询",notes = "list")
    public List<SysTenant> list(){
        return sysTenantService.list();
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查看详情",notes = "SysTenant")
    public SysTenant getSysTenantById(Long id){
            return sysTenantService.getById(id);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增",notes = "SysTenant")
    public boolean saveSysTenant(SysTenant sysTenant){
            return sysTenantService.save(sysTenant);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/修改",notes = "SysTenant")
    public boolean saveOrUpdate(SysTenant sysTenant){
            return sysTenantService.saveOrUpdate(sysTenant);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改",notes = "SysTenant")
    public boolean update(SysTenant sysTenant){
        return sysTenantService.updateById(sysTenant);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除",notes = "SysTenant")
    public boolean remove(Long id){
        return sysTenantService.removeById(id);
    }

}
