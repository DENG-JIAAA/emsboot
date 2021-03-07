package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.ResultVO;
import top.dj.POJO.VO.UserVO;
import top.dj.mapper.UserMapper;
import top.dj.service.UserService;

import javax.annotation.Resource;

/**
 * @author dj
 * @date 2021/1/8
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @GetMapping("/vo/{id}")
    public UserVO findUserVO(@PathVariable("id") Integer id) {
        return userService.findUserVO(id);
    }

    @GetMapping("/vo/name/{loginName}")
    public ResultVO<UserVO> findUserVO(@PathVariable("loginName") String loginName) {
        UserVO userVO = userService.findUserVO(loginName);
        boolean OK = userVO != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取用户成功" : "用户[" + loginName + "]不存在", userVO);
    }

    /**
     * 设置 Token拦截器后，前端Promise先异步请求前端API，前端API再发送request调用后端接口。
     * 目的是在前端 Request之前带上token，防止被拦截。
     *
     * @param page  第几页
     * @param limit 多少条数据
     * @return 将查到的原生user转换再封装
     */
    @GetMapping("/vo")
    public ResultVO<DataVO<UserVO>> getUserList(@RequestParam("page") Integer page,
                                                @RequestParam("limit") Integer limit) {
        DataVO<UserVO> userVO = userService.findUserVO(page, limit);
        boolean OK = userVO != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取用户列表成功" : "获取用户列表失败", userVO);
    }

    @PutMapping("/modifyProfile")
    public ResultVO<Boolean> modifyProfile(@RequestBody User user) {
        int i = userMapper.updateById(user);
        boolean OK = i == 1;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "修改资料成功" : "修改资料失败", OK);
    }
}
