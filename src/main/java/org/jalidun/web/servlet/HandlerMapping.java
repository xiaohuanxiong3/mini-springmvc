package org.jalidun.web.servlet;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @Description 主要是通过请求获取对应的处理器执行链。
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午6:49
 */
public interface HandlerMapping {

    /**
     * 根据请求获取处理器执行链
     * @param request
     * @return
     * @throws Exception
     */
    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
