package com.yanshen;

import com.yanshen.canal.CanalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CanalService {



    public static void main(String[] args) {
        SpringApplication.run(CanalService.class, args);
    }

    @Autowired
    CanalClient canalClient;


    @PostConstruct
    public Object start() {
        new Thread(new Runnable() {
            public void run() {
                //canalClients.doIt();
            }
        }).start();

        return new Object();
    }
}