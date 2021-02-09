//package top.dj.config;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * @author dj
// * @date 2021/2/7
// */
//@EnableWebSecurity
//public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
//    // 定义授权规则
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 授权规则
//        http.authorizeRequests().antMatchers("/permission").permitAll()
//                .antMatchers("/per/**").hasRole("admin");
//
//        // 开启自动配置的登录功能，如果没登录，没权限，就会来到登录页面。
//        http.formLogin().usernameParameter("loginName").passwordParameter("loginPwd")
//                // 处理请求的uri是：/userLogin
//                .loginProcessingUrl("/userLogin");
//
//        // 开启自动配置的注销功能
//        http.logout().logoutSuccessUrl("/"); // 注销成功以后来到 ”/“
//
//        // 开启浏览器记住用户功能
//        /*http.rememberMe().useSecureCookie(true).rememberMeParameter("remember");*/
//        http.rememberMe().rememberMeParameter("remember");
//    }
//
//    // 定义认证规则
//    // https://blog.csdn.net/syc000666/article/details/96862574
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("djosimon").password(new BCryptPasswordEncoder().encode("654321a")).roles("admin");
//    }
//}
