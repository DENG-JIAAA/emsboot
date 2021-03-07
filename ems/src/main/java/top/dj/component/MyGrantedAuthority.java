package top.dj.component;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

/**
 * 用于添加权限的 url 和 method
 *
 * @author dj
 * @date 2021/3/3
 */

public class MyGrantedAuthority implements GrantedAuthority {
    private String url;
    private String method;

    public MyGrantedAuthority() {
    }

    public MyGrantedAuthority(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public String toString() {
        return "MyGrantedAuthority{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyGrantedAuthority that = (MyGrantedAuthority) o;
        return Objects.equals(url, that.url) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getAuthority() {
        return this.url + ":" + this.method;
    }
}
