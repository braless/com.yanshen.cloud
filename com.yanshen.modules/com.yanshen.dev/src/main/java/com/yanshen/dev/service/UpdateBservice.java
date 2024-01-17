package com.yanshen.dev.service;

import com.yanshen.dev.mapper.TranscationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-18 17:14
 **/
@Service
public class UpdateBservice {

    @Autowired
    TranscationDAO dao;


    @Transactional(propagation = Propagation.SUPPORTS)
    public void setB() {
        //updateBservice.setB();
        dao.setBupdate();
        try {
            int a=1/0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //       update user set age=2 where id=2
    }
}
