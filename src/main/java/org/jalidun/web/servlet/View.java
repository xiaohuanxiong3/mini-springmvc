package org.jalidun.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午10:32
 */
public interface View {

    /**
     * 获取响应的内容类型
     * @return
     */
    String getContentType();

    /**
     * 渲染
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    void render(Map<String,?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
