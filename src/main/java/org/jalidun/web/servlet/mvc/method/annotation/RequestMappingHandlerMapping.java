package org.jalidun.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import org.jalidun.context.WebApplicationContext;
import org.jalidun.web.constant.Constant;
import org.jalidun.web.method.HandlerMethod;
import org.jalidun.web.servlet.HandlerExecutionChain;
import org.jalidun.web.servlet.HandlerInterceptor;
import org.jalidun.web.servlet.HandlerMapping;
import org.jalidun.web.servlet.mvc.RequestMappingInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description RequestMappingHandlerMapping
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午7:52
 */
public class RequestMappingHandlerMapping implements HandlerMapping {

    private Map<RequestMappingInfo, HandlerMethod> map;

    public RequestMappingHandlerMapping(Map<RequestMappingInfo, HandlerMethod> map) {
        this.map = map;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo(request.getServletPath(), request.getMethod());
        HandlerExecutionChain handlerExecutionChain = new HandlerExecutionChain();
        handlerExecutionChain.setHandler(map.get(requestMappingInfo));
        WebApplicationContext webApplicationContext = (WebApplicationContext) request.getServletContext().getAttribute(Constant.WEB_APPLICATION_CONTEXT);
        handlerExecutionChain.setInterceptorList((List<HandlerInterceptor>) webApplicationContext.getBean(Constant.INTERCEPTOR_LIST));
        return handlerExecutionChain;
    }
}
