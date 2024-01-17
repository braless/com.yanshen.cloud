package com.yanshen.dev.controller;

import com.alibaba.druid.util.StringUtils;
import com.yanshen.dev.base.ApiResult;
import com.yanshen.dev.base.ResultCode;
import com.yanshen.dev.tutils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.SmsClientUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: cyc
 * @Date: 2023/3/17 16:46
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("/send")
public class VerifyCodeController {

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/phone")
    public ApiResult sendMsg(@RequestParam("phone") String phone){

        String preFix="2022YZM_";

        String prePhone =preFix+phone;

        String v=redisUtil.get(prePhone);
        if (!StringUtils.isEmpty(v)){
            //
            long pre=Long.parseLong(v.split("_")[1]);
            if (System.currentTimeMillis()-pre<60000){
                return ApiResult.failed(ResultCode.FAILED,"请等一分钟后再尝试!");
            }
        }
        redisUtil.del(prePhone);

        String code=String.valueOf((int)((Math.random()+1)*100000));
        redisUtil.set(prePhone,code+"_"+System.currentTimeMillis(),10000);
        String preMsg="您登录网站的验证码是:"+code+",有效期1分钟,如非本人操作请忽视此信息";
        SmsClientUtil.sendPush(phone,preMsg);
        return ApiResult.success("验证码发送成功!");
    }
    @RequestMapping("/ok")
    public ApiResult getTopic(){
        long inTime=System.currentTimeMillis();
        long t =System.currentTimeMillis()/1000;
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long end =t+5;
        while (t<end) {
            t=System.currentTimeMillis()/1000;
            long curt=System.currentTimeMillis()/1000;
            if (curt!=t){
                log.info("游标时间:{}", dateFormat.format(new Date(t*1000)));
            }
        }
        log.info("请求进入时间:{},延期成功时间:{}",dateFormat.format(new Date(inTime)),dateFormat.format(new Date(end*1000)));
        return ApiResult.success("延期成功!");

    }

    /**
     * 图片预览接口
     * @param fileName
     * @return
     */
    @RequestMapping(value = "/privew/{fileName}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity preview(@PathVariable("fileName") String fileName)   {
        // path是指欲下载的文件的路径。
        String path = "D://images"+"/" + fileName;
        File file = new File(path);
        InputStreamResource resource= null;
        try {
            InputStream im =new FileInputStream(file);
            resource = new InputStreamResource(im);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
        HttpHeaders headers =new HttpHeaders();
        return new ResponseEntity<>(resource,headers, HttpStatus.OK);

    }
}
