package org.jalidun.web.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jalidun.context.WebApplicationContext;
import org.jalidun.web.constant.Constant;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午10:48
 */
public class DispatcherServlet extends HttpServlet {

    private HandlerMapping handlerMapping;

    private HandlerAdapter handlerAdapter;

    private ViewResolver viewResolver;

    @Override
    public void init() throws ServletException {
        try {
            ServletConfig servletConfig = getServletConfig();
            String contextConfigLocation = servletConfig.getInitParameter(Constant.CONTEXT_CONFIG_LOCATION);
            String springMvcXmlPath = getSpringMvcXmlPath(contextConfigLocation);
            System.out.println("SpringMVC配置文件路径解析完成:" + springMvcXmlPath);
            // 初始化web容器
            WebApplicationContext webApplicationContext = new WebApplicationContext(springMvcXmlPath, servletConfig.getServletContext());
            // 将容器存储到Servlet上下文,以备后续使用
            this.getServletContext().setAttribute(Constant.WEB_APPLICATION_CONTEXT, webApplicationContext);

            // 初始化handlerMapping
            this.handlerMapping = (HandlerMapping) webApplicationContext.getBean(Constant.HANDLER_MAPPING);
            // 初始化handlerAdapter
            this.handlerAdapter = (HandlerAdapter) webApplicationContext.getBean(Constant.HANDLER_ADAPTER);
            // 初始化ViewResolver
            this.viewResolver = (ViewResolver) webApplicationContext.getBean(Constant.VIEW_RESOLVER);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getSpringMvcXmlPath(String contextConfigLocation) {
        if (contextConfigLocation.startsWith(Constant.CLASSPATH)) {
            String path = contextConfigLocation.substring(Constant.CLASSPATH.length()).trim();
            String springMvcXmlPath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
            // 对路径解码,防止路径中有 % 等字符
            return URLDecoder.decode(springMvcXmlPath, Charset.defaultCharset());
        }
        return null;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HandlerExecutionChain mappedHandler = handlerMapping.getHandler(req);
            System.out.println("mappedHandler:" + mappedHandler);

            // 执行拦截器链的前置请求(preHandle)
            if (!mappedHandler.applyPreHandle(req,resp,mappedHandler.getHandler())) {
                return;
            }

            // 执行处理器方法
            HandlerAdapter adapter = handlerAdapter;
            ModelAndView mv = handlerAdapter.handle(req, resp, mappedHandler.getHandler());

            // 执行拦截器链的 postHandle 方法
            mappedHandler.applyPostHandle(req,resp,mappedHandler.getHandler(),mv);

            // 处理响应
            View view = viewResolver.resolveViewName((String) mv.getView(), Locale.CHINA);
            view.render(mv.getModel(), req, resp);

            // 执行拦截器链的 afterCompletion 方法
            mappedHandler.triggerAfterCompletion(req,resp,mappedHandler.getHandler(),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
