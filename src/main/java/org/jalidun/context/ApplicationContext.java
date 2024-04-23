package org.jalidun.context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jalidun.stereotype.Controller;
import org.jalidun.web.bind.annotation.RequestMapping;
import org.jalidun.web.constant.Constant;
import org.jalidun.web.method.HandlerMethod;
import org.jalidun.web.servlet.HandlerInterceptor;
import org.jalidun.web.servlet.mvc.RequestMappingInfo;
import org.jalidun.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.jalidun.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.jalidun.web.servlet.view.InternalResourceViewResolver;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 启动服务器时,初始化容器
 * @Author QiuYang Shen
 * @Date 2024/4/22 上午11:03
 */
public class ApplicationContext {
    private Map<String, Object> beanMap = new HashMap<String, Object>();

    public ApplicationContext(String xmlPath) throws Exception{
        // 组件扫描
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(xmlPath));
        Element componentScanElement = (Element)document.selectSingleNode("/beans/component-scan");
        String basePackage = componentScanElement.attributeValue("base-package");
        System.out.println("组件扫描:" + basePackage);
        Map<RequestMappingInfo, HandlerMethod> map = componentScan(basePackage);
        System.out.println("组件扫描后容器当前状态:" + beanMap);

        // 初始化视图解析器
        Element viewResolverBean = (Element)document.selectSingleNode("/beans/bean");
        String viewResolverClassName = viewResolverBean.attributeValue("class");
        Class<?> viewResolverClass = Class.forName(viewResolverClassName);
        Object viewResolverObject = viewResolverClass.newInstance();
        if (viewResolverObject instanceof InternalResourceViewResolver internalResourceViewResolver) {
            // 前缀
            Element prefixProperty = (Element)viewResolverBean.selectSingleNode("property[@name='prefix']");
            internalResourceViewResolver.setPrefix(prefixProperty.attributeValue("value"));

            // 后缀
            Element suffixProperty = (Element)viewResolverBean.selectSingleNode("property[@name='suffix']");
            internalResourceViewResolver.setSuffix(suffixProperty.attributeValue("value"));
        }
        beanMap.put(Constant.VIEW_RESOLVER, viewResolverObject);
        System.out.println("初始化视图解析器后容器当前状态:" + beanMap);

        // 创建所有拦截器对象
        Element interceptorsElement = (Element)viewResolverBean.selectSingleNode("/beans/interceptors");
        List<Element> interceptorBeans = interceptorsElement.elements("bean");
        List<HandlerInterceptor> interceptorList = new ArrayList<HandlerInterceptor>();
        for (Element interceptorBean : interceptorBeans) {
            String interceptorClassName = interceptorBean.attributeValue("class");
            Class<?> interceptorClass = Class.forName(interceptorClassName);
            HandlerInterceptor interceptor = (HandlerInterceptor) interceptorClass.newInstance();
            interceptorList.add(interceptor);
        }
        beanMap.put(Constant.INTERCEPTOR_LIST, interceptorList);
        System.out.println("创建所有拦截器对象后容器当前状态:" + beanMap);

        // 创建内置的handlerMapping和handlerAdaptor
        beanMap.put(Constant.HANDLER_MAPPING,new RequestMappingHandlerMapping(map));
        beanMap.put(Constant.HANDLER_ADAPTER,new RequestMappingHandlerAdapter());
        System.out.println("创建内置的handlerMapping和handlerAdaptor后容器当前状态:" + beanMap);


    }

    private Map<RequestMappingInfo, HandlerMethod> componentScan(String basePackage) throws Exception {
        String dirPath = Thread.currentThread().getContextClassLoader().getResource(basePackage.replace(".", "/")).getPath();
        File file = new File(URLDecoder.decode(dirPath));
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = new HashMap<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File classFile : files) {
                if (classFile.getName().endsWith(".class")) {
                    String className = basePackage + "." + classFile.getName().substring(0, classFile.getName().lastIndexOf("."));
                    Class<?> clazz = Class.forName(className);
                    Constructor<?> defaultCon = clazz.getDeclaredConstructor();
                    Object bean = defaultCon.newInstance();
                    beanMap.put(firstCharToLowerCase(clazz.getSimpleName()), bean);

                    // 如果clazz被@Controller注解标注
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        Method[] methods = clazz.getDeclaredMethods();
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(RequestMapping.class)) {
                                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                // 创建RequestMappingInfo 对象
                                RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
                                requestMappingInfo.setRequestURI(requestMapping.value()[0]);
                                requestMappingInfo.setRequestMethod(requestMapping.method().toString());
                                // 创建HandlerMethod 对象
                                HandlerMethod handlerMethod = new HandlerMethod();
                                handlerMethod.setHandler(bean);
                                handlerMethod.setMethod(method);

                                handlerMethodMap.put(requestMappingInfo, handlerMethod);
                            }
                        }
                    }
                }
            }
        }
        return handlerMethodMap;
    }

    private String firstCharToLowerCase(String simpleName) {
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }

    public Object getBean(String className) {
        return beanMap.get(className);
    }
}
