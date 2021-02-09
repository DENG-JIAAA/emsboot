package top.dj.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.dj.POJO.DO.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dj
 * @date 2021/2/4
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    /**
     * 在目标方法执行之前执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
            CORS复杂请求时会首先发送一个OPTIONS请求做嗅探，来测试服务器是否支持本次请求，请求成功后才会发送真实的请求
            OPTIONS请求不会携带任何数据（如果不判断，这个OPTIONS请求将被拦截，用户将无法登录）
            SpringMVC对预检请求的处理在preHandle之后（因为预检不携带数据，会被Token拦截器拦截）
            所以需要把所有OPTIONS请求放行
        */
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) return true;

        Map<String, Object> map = new HashMap<>();
        User user = null;
        // 获取请求头中的令牌
        String token = request.getHeader("token");
        // 验证令牌是否存在
        if (token != null) {
            user = redisTemplate.opsForValue().get(token);
        }

        // 令牌存在，是否合法（此令牌对应的用户是否存在）
        if (user != null) {
            // 只要用户还在继续操作，就要刷新token的剩余时间。
            redisTemplate.expire(token, Duration.ofMinutes(30L));
            return true;
        }
        // 令牌为null,user为null(非法令牌)
        // 令牌不为null,user为null(令牌已过期)
        map.put("code", token == null ? 50008 : 50014);
        map.put("message", token == null ? "非法令牌" : "令牌已过期");
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
