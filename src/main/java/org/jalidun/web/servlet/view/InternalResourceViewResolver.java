package org.jalidun.web.servlet.view;

import org.jalidun.web.servlet.View;
import org.jalidun.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午10:42
 */
public class InternalResourceViewResolver implements ViewResolver {

    private String prefix;

    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new InternalResourceView("text/html;charset=UTF-8", prefix + viewName + suffix);
    }
}
