package top.dj.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.dj.POJO.DO.User;
import top.dj.service.UserService;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dj
 * @date 2021/2/15
 */
@Component
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    /*
     * 不使用 @Autowired 进行 RedisTemplate 的装配
     * @Autowired 只能注入默认的bean，即是泛型为 Object 类型的那个
     * 使用 @Qualifier(value = "redisTemplate") 指定bean的id也不行
     * 而 @Resource(name = "redisTemplate") 指定一下id就行
     */
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;

    /**
     * 用户认证成功，生成 token 发送给前端
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {/*
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        response.setStatus(HttpStatus.OK.value());

        User loginUser = (User) authentication.getPrincipal();
        Set<String> keys = stringRedisTemplate.keys("*");
        String loginUsername = loginUser.getUsername();
        String redisUsername;
        // 默认 redis 中没有当前用户
        boolean isUserExist = false;
        // 登录成功，随机生成一个 token 令牌
        String token = loginUsername + "-" + UUID.randomUUID().toString();

        if (keys != null) {
            for (String key : keys) {
                String myKey = key.replace("\"", "");
                User redisUser = redisTemplate.opsForValue().get(myKey);
                redisUsername = redisUser != null ? redisUser.getUsername() : "";
                if (loginUsername.equals(redisUsername)) {
                    // redis 存在当前登录用户
                    isUserExist = true;
                    redisTemplate.rename(myKey, token);
                }
            }
        }
        // redis 中没有当前登录用户，则需要将 token和user 存入 redis，并且设置时效（1天）
        if (!isUserExist) redisTemplate.opsForValue().set(token, loginUser, Duration.ofDays(1L));

        // 响应 JSON
        ObjectMapper mapper = new ObjectMapper();
        ResTokenVO tokenVO = new ResTokenVO(20000, "认证成功！", token);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String str = mapper.writeValueAsString(tokenVO);
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    */
    }
}
