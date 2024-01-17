package com.yanshen.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>releP-hw4分支（济南项目迭代六）</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-08-14 10:09
 **/
public class SumUtil {


    public static Map<String, Object> doSum(Object object, Class clazz) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            SumBigDecimal sumBigDecimal = field.getAnnotation(SumBigDecimal.class);
            if (null != sumBigDecimal) {
                boolean sum = sumBigDecimal.required();
                if (sum) {
                    String fieldName = field.getName();
                    if (object instanceof ArrayList) {
                        ((List) object).forEach(o -> {
                            BigDecimal value;
                            try {
                                //打开私有访问
                                field.setAccessible(true);
                                //只获取标注注解的属性方法
                                //Method method = o.getClass().getMethod("get" + finalFieldName, new Class[]{});
                                value = new BigDecimal(field.get(o).toString());
                                //value =new BigDecimal(method.invoke(o, new Object[]{}).toString());
                                BigDecimal newVal = value.add((BigDecimal) (map.get(fieldName) == null ? new BigDecimal(0) : map.get(fieldName)));
                                map.put(fieldName, newVal);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        }
        return map;
    }

}
