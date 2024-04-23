package org.jalidun.ui;

import java.util.LinkedHashMap;

/**
 * @Description 将数据存储到域中
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午11:09
 */
public class ModelMap extends LinkedHashMap<String, Object> {

    public ModelMap() {

    }

    public ModelMap addAttribute(String key, Object value) {
        this.put(key, value);
        return this;
    }
}
