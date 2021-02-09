package top.dj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.dj.interceptor.TokenInterceptor;

/**
 * @author dj
 * @date 2021/2/4
 */
@Configuration // 拦截器配置类
public class MyInterceptorConfig implements WebMvcConfigurer {

    /**
     * 提前将Token拦截器实例化，防止redis失效
     * 并且添加拦截器的时候使用这个方法 而不是new拦截器
     *
     * @return
     */
    @Bean
    public HandlerInterceptor getTokenInterceptor() {
        return new TokenInterceptor();
    }

    /**
     * 注册登录拦截器
     * addPathPatterns -- 拦截的请求
     * excludePathPatterns -- 不拦截，直接放行的请求
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor())
                .addPathPatterns("/**")//拦截所有请求 token验证
                .excludePathPatterns("/userLogin"); //除去用户登录的请求
    }
}
