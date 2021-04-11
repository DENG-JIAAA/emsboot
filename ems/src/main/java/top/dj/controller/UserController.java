package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.ResultVO;
import top.dj.POJO.VO.UserQueryVO;
import top.dj.POJO.VO.UserVO;
import top.dj.mapper.UserMapper;
import top.dj.service.UserService;
import top.dj.service.impl.QiNiuService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
    @Autowired
    private QiNiuService qiNiuService;

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
     * 管理员user正在访问此接口，应该返回user管理的这个实践室的所有管理员。
     * 如果是超级管理员在进行访问，应该返回所有用户管理员。
     *
     * @param page  第几页
     * @param limit 多少条数据
     * @return 将查到的原生user转换再封装
     */
    @GetMapping("/vo")
    public ResultVO<DataVO<UserVO>> getUserList(HttpServletRequest request,
                                                @RequestParam("page") Integer page,
                                                @RequestParam("limit") Integer limit) {

        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        DataVO<UserVO> userVO = userService.findUserVO(page, limit, user);
        boolean OK = userVO != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取用户列表成功" : "获取用户列表失败", userVO);
    }

    @GetMapping("/vo/query")
    public ResultVO<DataVO<UserVO>> fetchUserListByQuery(HttpServletRequest request, UserQueryVO userQueryVO) {

        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        DataVO<UserVO> userVO = userService.fetchUserListByQuery(user, userQueryVO);
        boolean OK = userVO != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取用户条件查询列表成功" : "获取用户条件查询列表失败", userVO);
    }

    @PutMapping("/modifyProfile")
    public ResultVO<Boolean> modifyProfile(@RequestBody User user) {
        int i = userMapper.updateById(user);
        boolean OK = i == 1;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "修改资料成功" : "修改资料失败", OK);
    }

    @GetMapping("/changePwd")
    public ResultVO<Integer> changePwd(HttpServletRequest request,
                                       @RequestParam String oldPwd,
                                       @RequestParam String newPwd) {

        Integer i = userService.changePwd(request, oldPwd, newPwd);
        return new ResultVO<>(20000, i == 1 ? "修改密码成功" : "修改密码失败！！", i);
    }

    @PostMapping("/upload/avatar")
    public ResultVO<String> uploadAvatar(HttpServletRequest request) throws IOException {
        String avatarUrl = userService.uploadAndUpdate(request);
        return new ResultVO<>(20000, "上传头像，返回头像地址。", avatarUrl);
    }

}
