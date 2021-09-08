package top.dj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.Role;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.LoginVO;
import top.dj.POJO.VO.ResTokenVO;
import top.dj.POJO.VO.ResultVO;
import top.dj.POJO.VO.UserVO;
import top.dj.service.LoginService;
import top.dj.service.UserService;
import top.dj.utils.PinYinUtil;
import top.dj.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author dj
 * @date 2021/1/26
 */
@RestController
@RequestMapping
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    @Qualifier("pwdEncoder")
    private PasswordEncoder pwdEncoder;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 处理用户登录
     */
    @PostMapping("/login")
    public ResTokenVO login(LoginVO loginVO) {
        log.info("用户名为：" + loginVO.getLoginName() + "正在进行登录操作。");

        User loginUser = new User();
        BeanUtils.copyProperties(loginVO, loginUser);
        loginUser = userService.getOne(new QueryWrapper<>(loginUser));
        // 登录用户的确存在mysql数据库才 重置redis剩余时间
        if (loginUser != null) {
            Set<String> keys = stringRedisTemplate.keys("*");
            String loginUsername = loginUser.getUsername();
            String redisUsername;
            // 默认 redis 中没有当前用户
            boolean isUserExist = false;
            // 中文登录名的话会出现编码问题，将其转换为拼音
            loginUsername = PinYinUtil.getPinyin(loginUsername);
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
            // 将token返回前端
            return new ResTokenVO(20000, "登录成功，用户存在。", token);
        }
        return new ResTokenVO(20404, "用户不存在，或密码错误！", null);
    }

    /**
     * 处理用户注册
     */
    @PostMapping("/register")
    public ResultVO<Boolean> register(@RequestBody User user) {
        Boolean register = loginService.register(user);
        return new ResultVO<>(20000, register ? "注册用户成功" : "注册用户失败", register);
    }

    /**
     * 获取当前登录的原生User信息（DO）
     */
    @GetMapping("/getUserInfo")
    public ResultVO<User> getUserInfo(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        User one = null;
        if (user != null) {
            one = userService.getOne(new QueryWrapper<>(user));
        }
        return new ResultVO<>
                (one != null ? 20000 : 20404, one != null ? "获取登录用户成功" : "获取登录用户失败", one);
    }

    /**
     * 获取当前登录的转换User信息（VO）
     */
    @GetMapping("/getUserVOInfo")
    public ResultVO<UserVO> getUserVOInfo(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        UserVO userVO = null;
        if (user != null) {
            userVO = userService.findUserVO(user.getId());
            List<Role> roles = userVO.getAuthorities();
            // 用户权限列表不为空，进行转换。
            if (roles != null) {
                roles.forEach(role -> role.setAuthority(role.getAuthority().substring(5)));
            }
            user.setAuthorities(roles);
        }
        return new ResultVO<>
                (userVO != null ? 20000 : 20404, userVO != null ? "获取登录用户成功" : "获取登录用户失败", userVO);
    }
}
