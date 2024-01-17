package com.yanshen.controller;


import com.yanshen.service.LoginService;
import dto.TenantUserDTO;
import exception.TipException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.CommonUtil;
import util.MsgInfo;

import java.util.Objects;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 17:24
 **/
@RestController
@RequestMapping("/platformlogin")
public class LoginController {

    @Autowired
    LoginService loginService;
    /**
     * 登录
     *
     * @param tenantUserDTO
     * @return
     */
    @PostMapping(value="/m")
    public MsgInfo<TenantUserDTO> Login(@RequestBody TenantUserDTO tenantUserDTO) {
        MsgInfo<TenantUserDTO> msgInfo = new MsgInfo<>();
        //验证码存在则先验证再进行账号密码验证
        if(StringUtils.isNotBlank(tenantUserDTO.getYzm())){
            String key = tenantUserDTO.getTenantid()+tenantUserDTO.getYhzh();
            String yzmRedis = "";//platformLoginRPC.findRedis(key);
            if(!CommonUtil.equals(tenantUserDTO.getYzm(),yzmRedis)){
                throw new TipException("020001","验证码错误");
            }
        }
        msgInfo = loginService.platformLogin(tenantUserDTO);
        //tenantUserDTO.setZsxm(msgInfo.getData().getZsxm()==null?"":msgInfo.getData().getZsxm());
//        if (Objects.equals(msgInfo.getCode(), "000000")) {
//            if (StringUtils.isBlank(msgInfo.getData().getYzm())) {
//                //登录少于等于三次时无验证码，可登录成功；第四次时，无输入验证码，redis获取不到验证码，但是大于三次时可以生产验证码，需重新登录不生成日志
//                log.info(platformLoginService.getLog(tenantUserDTO, "loginLog", 0, 1, null));
//            }
//        }else {
//            log.info(platformLoginService.getLog(tenantUserDTO, "loginLog", 0, 0, msgInfo.getMessage()));
//        }
        return msgInfo;
    }
}
