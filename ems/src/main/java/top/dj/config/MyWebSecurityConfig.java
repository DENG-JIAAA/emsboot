package top.dj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.dj.component.MyAuthenticationProvider;
import top.dj.filter.TokenVerifyFilter;
import top.dj.handler.*;
import top.dj.interceptor.MyFilterSecurityInterceptor;
import top.dj.service.UserService;
import top.dj.service.impl.UserServiceImpl;

/**
 * @author dj
 * @date 2021/2/7
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler;
    @Autowired
    private MyLoginFailureHandler myLoginFailureHandler;
    @Autowired
    private MyLoginEntryPoint myLoginEntryPoint;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private MyLogoutHandler myLogoutHandler;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    private TokenVerifyFilter tokenVerifyFilter;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    /**
     * 认证器
     */
    // https://blog.csdn.net/syc000666/article/details/96862574
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
        auth.userDetailsService(userService)
                .passwordEncoder(pwdEncoder())
                .and()
                .authenticationProvider(myAuthenticationProvider);
    }

    /**
     * 定义授权规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 自定义入参
                .usernameParameter("loginName")
                .passwordParameter("loginPwd")
                // .loginPage("/login") 前后端完全分离，前端不能收到302，无需设计loginPage登录页
                // 配置处理登录表单的URL，与前端表单的action一致，即用户登录的提交地址。
                .loginProcessingUrl("/userLogin")
                // 自定义认证（登录）成功处理器
                .successHandler(myLoginSuccessHandler)
                // 自定义认证（登录）失败处理器
                .failureHandler(myLoginFailureHandler).permitAll().and()

                // 自定义异常处理器
                .exceptionHandling()
                // 自定义未登录或登录失败时，访问资源的处理方式，前后端分离前端不会302重定向。
                .authenticationEntryPoint(myLoginEntryPoint)
                // 自定义以登录，但权限不足，无法访问当前资源时的处理方式。
                .accessDeniedHandler(myAccessDeniedHandler).and()

                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                // 自定义注销处理器
                .addLogoutHandler(myLogoutHandler)
                .logoutSuccessHandler(myLogoutSuccessHandler).permitAll();

        // 配置开启跨域
        http.cors().and()
                // 允许跨站请求(关闭csrf防护)
                .csrf().disable()
                // 把 token 验证过滤器设置到 UsernamePasswordAuthenticationFilter 之前。
                .addFilterBefore(tokenVerifyFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // 基于token，不需要session，设置session无状态。
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')")
//                .antMatchers("/db/**").hasRole("DBA")
//                .antMatchers("/register").permitAll()

                // 注意：匹配路径时 越具体的路径要先匹配
                // 地址受保护，只有满足权限才可访问。不够权限就会用accessDeniedHandler()中的内容处理。
//                .antMatchers(HttpMethod.GET).hasAnyRole("VISITOR", "ADMIN", "SUPERADMIN")
//                .antMatchers(HttpMethod.POST).hasAnyRole("ADMIN", "SUPERADMIN")
//                .antMatchers(HttpMethod.PUT).hasAnyRole("ADMIN", "SUPERADMIN")
//                .antMatchers(HttpMethod.DELETE).hasRole("SUPERADMIN")
                .antMatchers("/api/homeData").permitAll()

                // 不需要鉴权的路径，指前一个antMatcher的地址未登录时可以访问，不受保护（不需要认证）。
                // .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                // 其他请求登录后即可访问
                .anyRequest().authenticated();

        // 单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个踢除下线
        // http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy());
        // 单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
        // http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);

        // 开启浏览器记住用户功能
        // http.rememberMe().useSecureCookie(true).rememberMeParameter("remember");
        // http.rememberMe().rememberMeParameter("remember");
    }

    /* 获取数据库中的信息存到User对象中 */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    /* 加密密码 */
    @Bean
    public PasswordEncoder pwdEncoder() {
        // 前后端分离需要注入一个PasswordEncoder来给successHandler使用。
        return new BCryptPasswordEncoder();
    }

    /* 配置认证管理器 */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
