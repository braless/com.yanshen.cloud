package com.yanshen.dev.tutils;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class CommonUtils {
    /**
     * 参数ASCII排序
     *
     * @param json
     * @return
     */
    public static String getAscString(JSONObject json) {
        Map<String, String> jsonMap = JSONObject.toJavaObject(json, Map.class);
        List<String> keys = new ArrayList<String>(json.keySet());
        Collections.sort(keys);
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < keys.size(); i++) {
            if (StringUtils.isNotEmpty(keys.get(i))) {
                map.put(keys.get(i), jsonMap.get(keys.get(i)));
            }
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
        return jsonObject.toString();
    }
}
