package top.dj.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.dj.POJO.DO.User;

import javax.annotation.Resource;
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
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Resource
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
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            log.info("放行CORS做复杂请求时会首先发送的OPTIONS嗅探请求。");
            return true;
        }

        Map<String, Object> map = new HashMap<>();
        User user = null;
        // 获取请求头中的令牌
        String token = request.getHeader("token");
        // 验证令牌是否存在
        // if (StringUtils.hasText(token)) {
        if (token != null) {
            user = redisTemplate.opsForValue().get(token);
        }

        // 令牌存在，是否合法（此令牌对应的用户是否存在）
        if (user != null) {
            // 只要用户还在继续操作，就要刷新token的剩余时间。
            redisTemplate.expire(token, Duration.ofDays(1L));
            return true;
        }
        /*
         * 令牌不存在（null） 或 非法（redis中不存在这样的token），自然都在redis中获取不到user。
         * 况且走到这一步，user 肯定为 null
         * 那么 token 要不直接为 null（令牌不存在）
         * 要么令牌存在但是 redis 找不到（已经过期）
         */
        map.put("code", token == null ? 50008 : 50014);
        map.put("message", token == null ? "未检测到用户令牌的存在，请先完成登录。" : "用户令牌非法或是已经过期，请重新登录。");
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;

    }
}
