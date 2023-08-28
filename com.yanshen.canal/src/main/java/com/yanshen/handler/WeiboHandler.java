package com.yanshen.handler;


import com.yanshen.module.WeiboModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;


/*
 * @Auther: cyc
 * @Date: 2023/3/7 20:33
 * @Description:
 */
@Slf4j
@Component
@CanalTable(value = "weibo_url")
public class WeiboHandler implements EntryHandler<WeiboModel>

     {
        @Override
        public void insert(WeiboModel user) {
            log.info("insert message  {}", user);
        }

        @Override
        public void update(WeiboModel before, WeiboModel after) {
            log.info("update before {} ", before);
            log.info("update after {}", after);
        }

        @Override
        public void delete(WeiboModel user) {
            log.info("delete  {}", user);
        }
    }

