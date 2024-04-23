package org.jalidun.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jalidun.web.servlet.HandlerAdapter;
import org.jalidun.web.servlet.ModelAndView;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午10:32
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setModel(null);
        modelAndView.setView("index");
        return modelAndView;
    }
}
