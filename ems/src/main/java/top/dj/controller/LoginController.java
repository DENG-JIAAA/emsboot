package top.dj.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.User;
import top.dj.POJO.DO.UserForAuth;
import top.dj.POJO.VO.LoginVO;
import top.dj.POJO.VO.ResTokenVO;
import top.dj.POJO.VO.ResultVO;
import top.dj.POJO.VO.UserVO;
import top.dj.service.LoginService;
import top.dj.service.UserForAuthService;
import top.dj.service.UserService;
import top.dj.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.UUID;

/**
 * @author dj
 * @date 2021/1/26
 */
@RestController
//@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserForAuthService userForAuthService;
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    /*@Autowired
    private AuthenticationManager authenticationManager;*/


    /*@GetMapping("/{id}")
    public void test(@PathVariable("id") Integer id) {
        System.out.println("前端登录页面传来的id：" + id);
    }*/

    /*@GetMapping("/")
    public void start() {
        System.out.println("start");
    }*/

    @GetMapping("/{loginName}/{loginPwd}")
    public Integer test(@PathVariable("loginName") String loginName,
                        @PathVariable("loginPwd") String loginPwd) {
        System.out.println("前端登录页面传来的数据：" + loginName + ", " + loginPwd);
        User user = new User();
        user.setLoginName(loginName);
        user.setLoginPwd(loginPwd);
        Boolean isExist = loginService.isThisUserExist(user);
        // 用户存在, 返回---1; 不存在,返回---0
        return isExist ? 1 : 0;
    }

    @PostMapping("/login")
    public ResultVO<Integer> login(@RequestBody LoginVO loginVO) {
        User user = new User();
        user.setLoginName(loginVO.getLoginName());
        user.setLoginPwd(loginVO.getLoginPwd());
        Boolean isExist = loginService.isThisUserExist(user);
        // 用户存在, 返回---1; 不存在,返回---0
        ResultVO<Integer> resultVO = new ResultVO<>();
        resultVO.setCode(20000);
        resultVO.setData(isExist ? 1 : 0);
        return resultVO;
    }

    @PostMapping("/login2")
    public ResultVO<String> login2(@RequestBody LoginVO loginVO) {
        System.out.println(loginVO);
        User user = new User();
        BeanUtils.copyProperties(loginVO, user);
        user = loginService.getUserByLoginInfo(user);
        if (user != null) {
            // 登录成功，生成一个UUID做令牌
            String token = UUID.randomUUID().toString();
            // 将token存入redis
            redisTemplate.opsForValue().set(token, user, Duration.ofMinutes(30L));
            return new ResultVO<>(20000, "登录成功", token);
        }
        return new ResultVO<>(104, "登录失败", null);
    }

    /**
     * 处理用户登录
     */
    @PostMapping("/userLogin")
    public ResTokenVO userLogin(@RequestBody LoginVO loginVO) {
        User user = new User();
        BeanUtils.copyProperties(loginVO, user);
        user = loginService.getUserByLoginInfo(user);

        UserForAuth userForAuth = new UserForAuth();
        userForAuth.setLoginName(loginVO.getLoginName());
        Wrapper<UserForAuth> wrapper = new QueryWrapper<>(userForAuth);
        userForAuthService.getOne(wrapper);

        if (user != null) {
            // 登录成功，生成一个UUID做令牌
            String token = UUID.randomUUID().toString();
            // 将token存入redis
            redisTemplate.opsForValue().set(token, user, Duration.ofMinutes(30L));
            // 将token返回前端
            return new ResTokenVO(20000, "登录成功，用户存在。", token);
        }
        return new ResTokenVO(20004, "登录失败，用户不存在。", null);
    }

    /**
     * 获取已经登录的原生User信息（DO）
     */
    @GetMapping("getUserOfLogin1")
    public ResultVO<User> getUserOfLogin1(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        return new ResultVO<>(20000, "获取登录用户信息成功", user);
    }

    /**
     * 获取已经登录的转换User信息（VO）
     */
    @GetMapping("getUserOfLogin2")
    public ResultVO<UserVO> getUserOfLogin2(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        UserVO userVO = null;
        if (user != null) {
            userVO = userService.findUserVO(user.getId());
        }
        return new ResultVO<>(20000, "获取登录用户信息成功", userVO);
    }
}
