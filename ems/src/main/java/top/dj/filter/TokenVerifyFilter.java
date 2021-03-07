package top.dj.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import top.dj.POJO.DO.User;
import top.dj.service.UserService;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 验证请求携带的 token 是否有效
 *
 * @author dj
 * @date 2021/2/9
 */
@Component
public class TokenVerifyFilter extends GenericFilterBean {
    @Autowired
    private UserService userService;
    @Resource
    private RedisTemplate<String, User> redisTemplate;

    /**
     * SpringSecurity 验证 token 的过滤器，验证成功设置携带此 token 的用户为登录状态，并放行请求。
     * 可以自定义一个过滤器，在 SpringSecurity 的登录过滤器（UsernamePasswordAuthenticationFilter）之前先拦截请求，
     * 然后进行 token 验证，如果验证通过了就把当前用户设置到 SecurityContextHolder 中，这样就完成了验证和登录。
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            // 从请求头获取token
            String token = request.getHeader("token");
            // 如果用户携带了 token 就验证一下，没携带也没关系。
            if (StringUtils.hasText(token)) {
                // 从 redis 中通过 token 反序列化一个用户
                User redisUser = redisTemplate.opsForValue().get(token);
                if (redisUser != null) {
                    // 执行到此处代表 当前用户在 redis 中存在
                    // 用户携带了 token，且这个 token 合法没过期（redis中还有ttl）。
                    UserDetails userDetails = userService.loadUserByUsername(redisUser.getLoginName());
                    if (null != userDetails) {
                        // 执行到此处代表 当前用户在 mysql 中存在，
                        // 将从 mysql 中获取的用户信息封装成 UsernamePasswordAuthenticationToken，
                        // 用户访问资源将不需要进行表单登录验证。
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                                );
                        // 设置认证成功的 token
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (Exception e) {
            // 登录发生异常，但要继续走其余过滤器的逻辑。
            e.printStackTrace();
        }
        // 继续执行 SpringSecurity 的过滤器
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
