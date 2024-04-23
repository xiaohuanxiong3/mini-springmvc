package org.jalidun.web.servlet;

import java.util.Locale;

/**
 * @Description 解析逻辑视图名称,返回视图对象
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午10:40
 */
public interface ViewResolver {

    /**
     * 解析逻辑视图名称，返回视图对象
     * @param viewName
     * @param locale
     * @return
     * @throws Exception
     */
    View resolveViewName(String viewName, Locale locale) throws Exception;
}
