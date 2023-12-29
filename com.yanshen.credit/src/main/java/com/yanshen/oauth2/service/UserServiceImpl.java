package com.yanshen.oauth2.service;

import com.yanshen.constant.MessageConstant;
import com.yanshen.dto.AuthUserDto;
import com.yanshen.dto.SecurityUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理业务类
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserClient sysUserClient;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter("client_id");
        String type = request.getParameter("type");
        AuthUserDto userDto;
        if(StringUtils.isNotEmpty(clientId) && clientId.equals("xrw-cloud-user-portal") && StringUtils.isNotEmpty(type)){
            userDto = sysUserClient.loadPortalUserByUsername(username, Integer.parseInt(type));
        } else {
            userDto = sysUserClient.loadUserByUsername(username);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // 加密
            String encodedPassword = passwordEncoder.encode(userDto.getPassword().trim());
            System.out.println("密码:"+encodedPassword);
        }
        if (userDto == null) {
            //保存企业信息
            AuthUserDto insert =new AuthUserDto();
            insert.setUsername(username);
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        userDto.setClientId(clientId);
        SecurityUser securityUser = new SecurityUser(userDto);
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }

}