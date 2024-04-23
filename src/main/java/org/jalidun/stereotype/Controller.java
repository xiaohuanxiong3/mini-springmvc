package org.jalidun.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 用来标注处理器，被标注的处理器，纳入IoC容器的管理。该注解只允许出现在类上，另外可以被反射机制读取。
 * @Author QiuYang Shen
 * @Date 2024/4/21 上午11:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}

