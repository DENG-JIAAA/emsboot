package top.dj.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author dj
 * @date 2021/3/2
 */

@RestController
@RequestMapping
public class HomeController {

    @GetMapping("/index")
    public String index() {
        return "用户正在浏览主页面";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam("username") String username) {
//        System.out.println("username = " + username);
//        return "用户登录页面";
//    }

    @GetMapping("/authUser")
    public String getAuthUser() {
        return "get auth user";
    }

    @PostMapping("/authUser")
    public String addAuthUser() {
        return "add auth user";
    }

    @PutMapping("/authUser")
    public String updateAuthUser() {
        return "update auth user";
    }

    @DeleteMapping("/authUser")
    public String deleteAuthUser() {
        return "delete auth user";
    }


}
