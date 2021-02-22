package top.dj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import top.dj.component.MyAuthenticationProvider;
import top.dj.handler.*;
import top.dj.service.UserForAuthService;
import top.dj.service.impl.UserForAuthServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dj
 * @date 2021/2/7
 */
@EnableWebSecurity
// 开启权限注解，在Controller类的方法上使用@PreAuthorize("hasRole('admin')")进行权限控制
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthWebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*@Autowired
    private UserDetailsService userDetailsService;*/
    @Autowired
    private UserForAuthService userForAuthService;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private UnLoginHandler unLoginHandler;
    @Autowired
    private NoAccessDeniedHandler noAccessDeniedHandler;
    @Autowired
    private MyLogoutHandler myLogoutHandler;
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 自定义入参
                .usernameParameter("username")
                .passwordParameter("password")
                // .loginPage("/login") 前后端分离，前端不能收到302，无需设计loginPage登录页
                // 配置处理登录表单的URL，与前端表单的action一致，即用户登录的提交地址。
                .loginProcessingUrl("/userLogin")

                // 自定义认证（登录）成功处理器
                .successHandler(loginSuccessHandler)

                // 自定义认证（登录）失败处理器
                .failureHandler(loginFailureHandler).permitAll()

                /*.and().sessionManagement()
                // .invalidSessionUrl("/session/invalid") session过期后跳转的url
                .invalidSessionStrategy(new InvalidSessionStrategy() {
                    @Override
                    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Result result = new Result(CodeUtil.INVALID_SESSION.getCode(), CodeUtil.INVALID_SESSION.getMessage(), "invalid");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(objectMapper.writeValueAsString(result));
                        out.flush();
                        out.close();
                    }
                })*/

                // 自定义异常处理器
                // .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and()
                .and().exceptionHandling()
                // 自定义未登录或登录失败时，访问资源的处理方式
                // 前后端分离前端不会302重定向，所以要重写AuthenticationEntryPoint
                .authenticationEntryPoint(unLoginHandler)
                // 自定义以登录，但权限不足，无法访问当前资源时的处理方式
                .accessDeniedHandler(noAccessDeniedHandler)

                .and().logout()
                // 配置注销的URL，请求方式为GET
                .logoutUrl("/userLogout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                        // 此处可做一些清除工作
                    }
                })
                // 自定义注销成功处理器
                .logoutSuccessHandler(myLogoutHandler).permitAll().and();

        // 把token验证过滤器设置到UsernamePasswordAuthenticationFilter之前。
        // .addFilterBefore(tokenVerifyFilter, UsernamePasswordAuthenticationFilter.class);


        // 未登录请求资源
        // .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());

        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')")
//                .antMatchers("/db/**").hasRole("DBA")
//                .antMatchers("/register").permitAll()

                .antMatchers("/userLogin").permitAll()
                // 地址受保护，只有满足权限才可访问。不够权限就会用accessDeniedHandler()中的内容处理。
                .antMatchers(HttpMethod.GET, "/api/addData").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/delData").hasRole("SUPERADMIN")
                // 不需要鉴权的路径，指前一个antMatcher的地址未登录时可以访问，不受保护。
                .antMatchers(HttpMethod.GET, "/api/getData").permitAll()
                // 设置哪些路径可以直接访问，不需要认证。
                // .antMatchers("/userLogin").permitAll()
                /*.antMatchers(HttpMethod.POST, "/api/data").hasAnyAuthority("add")*/
                .antMatchers("/home").hasAuthority("base")
                .antMatchers("/getUserOfLogin1").permitAll()

                // 其他请求登录后即可访问
                .anyRequest().authenticated();

//        // 关闭csrf防护
//        http.csrf().disable();
//        // 基于token，不需要session
//        // .sessionManagement()
//        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // 解决跨域问题
//        http.cors().and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();

        // 配置跨域 以及 允许跨站请求
        http.cors().and().csrf().disable();

        // 设置不使用session，无状态
        /*http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

        //单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个踢除下线
        //http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy());
        //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
        //http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);

    }

    /**
     * 认证器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*super.configure(auth);*/
        auth.userDetailsService(userForAuthService).passwordEncoder(pwdEncoder());
        // 加入自定义的安全认证
        auth.authenticationProvider(myAuthenticationProvider);
    }

    /* 获取数据库中的信息存到User对象中 */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserForAuthServiceImpl();
    }

    /* 加密密码 */
    @Bean
    PasswordEncoder pwdEncoder() {
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
