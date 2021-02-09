package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.ResTokenVO;
import top.dj.POJO.VO.ResultVO;
import top.dj.POJO.VO.UserVO;
import top.dj.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dj
 * @date 2021/1/8
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @GetMapping("/vo/{id}")
    public UserVO findUserVO(@PathVariable("id") Integer id) {
        return userService.findUserVO(id);
    }

    @GetMapping("/vo/name/{loginName}")
    public ResultVO<UserVO> findUserVO(@PathVariable("loginName") String loginName) {
        UserVO userVO = userService.findUserVO(loginName);
        return new ResultVO<>(20000, "获取用户", userVO);
    }

    /**
     * 未设置Token拦截器之前，前端可直接调用这个接口。
     *
     * @param page  第几页
     * @param limit 页记录数
     * @return 将查到的原生user转换再封装
     */
    /*@GetMapping("/vo/{page}/{limit}")
    public DataVO<UserVO> findUserVO(@PathVariable("page") Integer page,
                                     @PathVariable("limit") Integer limit) {
        return userService.findUserVO(page, limit);
    }*/

    /**
     * 设置Token拦截器后，前端Promise先异步请求前端API，前端API再发送request调用后端接口。
     * 目的是在前端request之前带上token，防止被拦截。
     * @param page 第几页
     * @param limit 多少条数据
     * @return 将查到的原生user转换再封装
     */
    @GetMapping("/vo/userList")
    public ResultVO<DataVO<UserVO>> getUserList(@RequestParam("page") Integer page,
                                                @RequestParam("limit") Integer limit) {
        DataVO<UserVO> userVO = userService.findUserVO(page, limit);
        return new ResultVO<>(20000, "获取用户列表", userVO);
    }

    @GetMapping("/info")
    public ResTokenVO getUserInfo(HttpServletRequest request) {
        // 获取Headers中的参数
        String token = request.getHeader("token");
        User user = redisTemplate.opsForValue().get(token);
        return null;
    }

}
