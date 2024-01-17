package com.yanshen.dev.service;

import com.yanshen.dev.mapper.TranscationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-18 16:52
 **/
@Service
public class TranscationService {

    @Autowired
    TranscationDAO dao;

    @Autowired
    UpdateBservice updateBservice;

    @Autowired
    TranscationService transcationService;


    /**
     * 在spring中，当一个方法开启事务时，spring创建这个方法的类的bean对象，则创建该对象的代理对象。
     * spring 中调用bean对象的方法才会去判断方法上的注解。在代理bean对象中，一个方法调用本身的另一个方法，
     * 实则调用的代理对象的原始对象（不属于 spring bean）的方法，
     * 调用方法时不会去判断方法上的注解。这就是传播机制不生效的原因。
     */
    //@Transactional
    public String setA() {
        //    update user set age=3 where id=1
        dao.update();
        //dao.setBupdate();
        //int a= 1/0;
        //updateBservice.setB();
       transcationService.setB();
        return "OK";

    }

    /**
     *
     *
     * A有事务, B没有事务 B异常 ---->全部回滚
     *A有事务, B有事务 B异常 ---->全部回滚
     * A没有事务 ,B有事务 --->全部提交事务
     */
    @Transactional
    public void setB() {
        //updateBservice.setB();
        dao.setBupdate();
        int a=1/0;
        //       update user set age=2 where id=2
    }

    /**
     * 编程式事务
     */
//    try {
//        //TODO something
//        transactionManager.commit(status);
//    } catch (Exception e) {
//        transactionManager.rollback(status);
//        throw new InvoiceApplyException("异常失败");
//    }


}
