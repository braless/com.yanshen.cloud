package com.yanshen.dev;

import com.alibaba.druid.pool.DruidDataSource;
//import com.stanwind.sync.anno.EnableSyncListener;
import com.yanshen.dev.canal.CanalClients;
import com.yanshen.dev.listener.CanalListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@MapperScan("com.yanshen.dev")
@SpringBootApplication
//@EnableSyncListener
@EnableFeignClients
@EnableDiscoveryClient
public class DevService {

    public static void main(String[] args) {
        SpringApplication.run(DevService.class, args);
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Autowired
    CanalListener canalListener;

    @Autowired
    CanalClients canalClients;


    @PostConstruct
    public Object start() {
        new Thread(new Runnable() {
            public void run() {
                canalClients.doIt();
            }
        }).start();

        return new Object();
    }


}
