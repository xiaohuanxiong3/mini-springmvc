package org.jalidun.web.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 用来标注处理器方法，允许标注方法和类，可以被反射机制读取。
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午6:10
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    /**
     * 指定请求路径
     * @return
     */
    String[] value() default {};

    /**
     * 指定请求方式
     * @return
     */
    RequestMethod method() default RequestMethod.GET;
}
