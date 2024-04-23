package org.jalidun.web.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jalidun.web.servlet.View;

import java.util.Map;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/21 下午10:35
 */
public class InternalResourceView implements View {

    private String contentType;

    private String path;

    public InternalResourceView(String contentType, String path) {
        this.contentType = contentType;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应内容类型
        response.setContentType(contentType);
        // 向request域中绑定数据
        if (model != null) {
            model.forEach(request::setAttribute);
        }
        // 转发
        request.getRequestDispatcher(path).forward(request, response);
    }
}
