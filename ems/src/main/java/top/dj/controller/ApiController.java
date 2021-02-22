package top.dj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dj
 * @date 2021/2/15
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/getData")
    public String getData() {
        return "getData......";
    }

    @GetMapping("/addData")
    public String addData() {
        return "addData......";
    }

    @GetMapping("/delData")
    public String deleteData() {
        return "delData......";
    }
}
