//package top.dj.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.GenericFilterBean;
//import top.dj.POJO.DO.User;
//import top.dj.service.UserService;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
///**
// * @author dj
// * @date 2021/2/9
// */
//@Component
//public class TokenVerifyFilter extends GenericFilterBean {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RedisTemplate<String, User> redisTemplate;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        try {
//            HttpServletRequest request = (HttpServletRequest) servletRequest;
//            // 从请求头获取token
//            String token = request.getHeader("token");
//            if (StringUtils.hasText(token)) {
//                User user = redisTemplate.opsForValue().get(token);
//                if (null != user) {
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(token, "", user.getUserRole());
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
