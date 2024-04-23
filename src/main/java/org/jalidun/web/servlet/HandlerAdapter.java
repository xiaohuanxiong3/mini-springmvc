package org.jalidun.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @Description 通过处理器适配器调用处理器方法
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午10:23
 */
public interface HandlerAdapter {

    /**
     * 执行处理器方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
