//package top.dj.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import top.dj.handler.NoAccessDeniedHandler;
//import top.dj.handler.UnLoginHandler;
//
///**
// * @author dj
// * @date 2021/2/7
// */
//@EnableWebSecurity
//public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    /*@Bean
//    public UserDetailsRepository userDetailsRepository() {
//        UserDetailsRepository userDetailsRepository = new UserDetailsRepository();
//        // 为了让我们的登录能够运行 这里我们初始化一个用户Felordcn 密码采用明文 当你在密码12345上使用了前缀{noop} 意味着你的密码不使用加密，authorities 一定不能为空 这代表用户的角色权限集合
//        UserDetails djosimon = User.withUsername("djosimon").password("{noop}123456").authorities(AuthorityUtils.NO_AUTHORITIES).build();
//        userDetailsRepository.createUser(djosimon);
//        return userDetailsRepository;
//    }*/
//
//    /*@Bean
//    public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository) {
//        return new UserDetailsManager() {
//            @Override
//            public void createUser(UserDetails userDetails) {
//                userDetailsRepository.createUser(userDetails);
//            }
//
//            @Override
//            public void updateUser(UserDetails userDetails) {
//                userDetailsRepository.updateUser(userDetails);
//            }
//
//            @Override
//            public void deleteUser(String username) {
//                userDetailsRepository.deleteUser(username);
//            }
//
//            @Override
//            public void changePassword(String oldPwd, String newPwd) {
//                userDetailsRepository.changePassword(oldPwd, newPwd);
//            }
//
//            @Override
//            public boolean userExists(String username) {
//                return userDetailsRepository.userExists(username);
//            }
//
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return userDetailsRepository().loadUserByUsername(username);
//            }
//        };
//    }*/
//
//    // 定义授权规则
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        // 用户配置
//
//        // 设置未登录或登录失败时访问资源的 处理方式
//        http.exceptionHandling().authenticationEntryPoint(new UnLoginHandler());
//        // 设置权限不足无法访问当前资源时的 处理方式
//        http.exceptionHandling().accessDeniedHandler(new NoAccessDeniedHandler());
//
//        /*// 授权规则
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
//        http.rememberMe().useSecureCookie(true).rememberMeParameter("remember");
//        http.rememberMe().rememberMeParameter("remember");*/
//    }
//
//    // 定义认证规则
//    // https://blog.csdn.net/syc000666/article/details/96862574
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("djosimon").password(new BCryptPasswordEncoder().encode("654321a")).roles("admin");*/
//        auth.userDetailsService(userDetailsService).passwordEncoder(pwdEncoder());
//    }
//
//    @Bean
//    PasswordEncoder pwdEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
