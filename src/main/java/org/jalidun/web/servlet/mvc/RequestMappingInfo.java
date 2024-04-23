package org.jalidun.web.servlet.mvc;

import java.util.Objects;

/**
 * @Description
 * @Author QiuYang Shen
 * @Date 2024/4/22 下午8:11
 */
public class RequestMappingInfo {
    private String requestURI;

    private String requestMethod;

    public RequestMappingInfo() {

    }

    public RequestMappingInfo(String requestURI, String requestMethod) {
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || hashCode() != o.hashCode()) return false;
        RequestMappingInfo that = (RequestMappingInfo) o;
        return Objects.equals(requestURI, that.requestURI) && Objects.equals(requestMethod, that.requestMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestURI, requestMethod);
    }

    @Override
    public String toString() {
        return "RequestMappingInfo{" +
                "requestURI='" + requestURI + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                '}';
    }
}
