package com.yanshen.test;

import com.yanshen.utils.SumUtil;
import com.yanshen.entity.CountSum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SumTest {


    public static void main(String[] args) {
        List<CountSum> list =new ArrayList<>();
        for (int i = 0; i <101 ; i++) {
            CountSum sum =new CountSum();
            sum.setAge(new BigDecimal(i*2));
            sum.setAmount(new BigDecimal(i));
            sum.setTall(Double.valueOf(i));
            sum.setName(i+"A");
            sum.setSize(i);
            list.add(sum);
        }
        Map<String, Object> sum = SumUtil.doSum(list,CountSum.class);
        CountSum sum1 =new CountSum();

       System.out.println("map数据是:"+sum);
        System.out.println("测试数据大小:"+list.size());
    }
}
