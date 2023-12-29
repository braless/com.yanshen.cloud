package com.yanshen.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 登录用户信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AuthUserDto implements Serializable {
    private Long id;
    private String nickName;
    private String username;
    private String userCode;
    private String companyCode;
    private String deptCode;
    private String password;
    private String deptName;
    private Boolean enabled;
    private String clientId;
    private List<String> roles;
    private List<Long> dataScopes;

    public AuthUserDto(UserDto dto, List<Long> depIds, Set<String> roles) {
        this.id = dto.getId();
        this.nickName = dto.getNickName();
        this.username = dto.getUsername();
        this.userCode = dto.getUserCode();
        this.companyCode = dto.getCompanyCode();
        this.deptCode = dto.getDeptCode();
        this.password = dto.getPassword();
        this.deptName = dto.getDeptName();
        this.enabled = dto.getEnabled();
        this.clientId = clientId;
        this.roles = new ArrayList<>(roles);
        this.dataScopes = depIds;
    }

    public AuthUserDto(SysOrganPerson person) {
        this.id = person.getPersonId();
        this.nickName = person.getPersonNickname();
        this.username = person.getPhone();
        this.password = person.getPassword();
        this.enabled = (person.getStatus() == 0);
        this.clientId = clientId;
    }

}