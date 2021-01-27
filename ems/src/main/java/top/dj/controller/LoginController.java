package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.User;
import top.dj.service.LoginService;

/**
 * @author dj
 * @date 2021/1/26
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    /*@GetMapping("/{id}")
    public void test(@PathVariable("id") Integer id) {
        System.out.println("前端登录页面传来的id：" + id);
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

    @GetMapping("/user")
    public Integer test2(String username, String password) {
        User user = new User();
        user.setLoginName(username);
        user.setLoginPwd(password);
        Boolean isExist = loginService.isThisUserExist(user);
        // 用户存在, 返回---1; 不存在,返回---0
        return isExist ? 1 : 0;
    }

}
