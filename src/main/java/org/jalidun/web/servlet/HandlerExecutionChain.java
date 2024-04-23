package org.jalidun.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午10:58
 */
public class HandlerExecutionChain {

    private Object handler;

    private List<HandlerInterceptor> interceptorList;

    private int interceptorIndex = -1;

    public HandlerExecutionChain() {

    }

    public HandlerExecutionChain(Object handler, List<HandlerInterceptor> interceptorList) {
        this.handler = handler;
        this.interceptorList = interceptorList;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public int getInterceptorIndex() {
        return interceptorIndex;
    }

    public void setInterceptorIndex(int interceptorIndex) {
        this.interceptorIndex = interceptorIndex;
    }

    public List<HandlerInterceptor> getInterceptorList() {
        return interceptorList;
    }

    public void setInterceptorList(List<HandlerInterceptor> interceptorList) {
        this.interceptorList = interceptorList;
    }

    public boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        for (int i = 0; i < interceptorList.size(); i++) {
            HandlerInterceptor interceptor = interceptorList.get(i);
            if (!interceptor.preHandle(request, response, handler)) {
                triggerAfterCompletion(request, response, handler,null);
                return false;
            }
            interceptorIndex = i;
        }
        return true;
    }

    public void applyPostHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        for (int i=interceptorList.size()-1; i>=0; i--) {
            HandlerInterceptor interceptor = interceptorList.get(i);
            interceptor.postHandle(request,response,handler,modelAndView);
        }
    }

    public void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        for (int i=interceptorIndex; i>=0; i--) {
            HandlerInterceptor interceptor = interceptorList.get(i);
            interceptor.afterCompletion(request,response,handler,ex);
        }
    }
}
