package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.UserForAuth;
import top.dj.service.UserForAuthService;

/**
 * @author dj
 * @date 2021/2/14
 */
@RestController
@RequestMapping("/userForAuth")
public class UserForAuthController /*extends BaseController<UserForAuth>*/ {
    @Autowired
    private UserForAuthService userForAuthService;

    @GetMapping("/{id}")
    public UserForAuth getOne(@PathVariable("id") Integer id) {
        return userForAuthService.getById(id);
    }
}
