package top.dj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @author dj
 * @date 2021/1/12
 */
@Configuration
public class MyCorsConfig implements WebMvcConfigurer {

    /**
     * SpringBoot 中使用 SpringSecurity，
     * 需要在 WebSecurityConfigurerAdapter 和 WebMvcConfigurer 中同时开启跨域。
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
