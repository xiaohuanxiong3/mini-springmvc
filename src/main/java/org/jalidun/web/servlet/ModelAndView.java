package org.jalidun.web.servlet;

import org.jalidun.ui.ModelMap;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午11:12
 */
public class ModelAndView {

    // View instance or view name String.
    private Object view;

    private ModelMap model;

    public ModelAndView() {

    }

    public ModelAndView(Object view, ModelMap model) {
        this.view = view;
        this.model = model;
    }

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public ModelMap getModel() {
        return model;
    }

    public void setModel(ModelMap model) {
        this.model = model;
    }

    /**
     * 方法待实现
     * @param viewName
     */
    public void setViewName(String viewName) {
        // TODO
    }
}
