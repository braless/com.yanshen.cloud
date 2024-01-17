package com.yanshen.oauth2.service;

import com.yanshen.dto.AuthUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface SysUserClient {

    AuthUserDto loadUserByUsername(@Param("username") String username);

    AuthUserDto loadPortalUserByUsername(@RequestParam("username") String username, @RequestParam("type") Integer type);

    void saveUser(AuthUserDto authUserDto);
}
