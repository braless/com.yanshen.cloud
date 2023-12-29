package com.yanshen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping(value = "/{id}")
    public String getOrder(@PathVariable String id) {
        logger.info("进入/order/方法,入参:{}", id);
        return "order id : " + id;
    }
}
