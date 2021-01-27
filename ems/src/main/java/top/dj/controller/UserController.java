package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.UserVO;
import top.dj.service.UserService;

/**
 * @author dj
 * @date 2021/1/8
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {
    @Autowired
    private UserService userService;

    @GetMapping("/vo/{id}")
    public UserVO findUserVO(@PathVariable("id") Integer id) {
        return userService.findUserVO(id);
    }

    @GetMapping("/vo/name/{loginName}")
    public UserVO findUserVO(@PathVariable("loginName") String loginName) {
        return userService.findUserVO(loginName);
    }

    @GetMapping("/vo/{page}/{limit}")
    public DataVO<UserVO> findUserVO(@PathVariable("page") Integer page,
                                     @PathVariable("limit") Integer limit) {
        return userService.findUserVO(page, limit);
    }

}
