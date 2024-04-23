package org.jalidun.context;

import jakarta.servlet.ServletContext;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/22 上午11:29
 */
public class WebApplicationContext extends ApplicationContext{

    private ServletContext servletContext;

    public WebApplicationContext(String xmlPath,ServletContext servletContext) throws Exception {
        super(xmlPath);
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
