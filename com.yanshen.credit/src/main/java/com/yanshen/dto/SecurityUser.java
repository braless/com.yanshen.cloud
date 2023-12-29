package com.yanshen.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 登录用户信息
 */
@Data
public class SecurityUser implements UserDetails {

    /**
     * ID
     */
    private Long id;
    private String nickName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 部门
     */
    private String deptName;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 登录客户端ID
     */
    private String clientId;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    private List<Long> dataScopes;

    private String userCode;

    private String companyCode;

    private String deptCode;

    public SecurityUser() {

    }

    public SecurityUser(AuthUserDto userDto) {
        this.id = userDto.getId();
        this.nickName = userDto.getNickName();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.deptName = userDto.getDeptName();
        this.enabled = userDto.getEnabled();
        this.clientId = userDto.getClientId();
        if (userDto.getRoles() != null) {
            authorities = new ArrayList<>();
            userDto.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        } else {
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("admin"));
        }
        this.dataScopes = userDto.getDataScopes();
        this.userCode = userDto.getUserCode();
        this.companyCode = userDto.getCompanyCode();
        this.deptCode = userDto.getDeptCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}