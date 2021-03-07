package top.dj.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author dj
 * @date 2021/2/15
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/homeData")
    public String homeData() {
        return "homeData......";
    }

    @GetMapping("/getData")
    public String getData() {
        return "getData......";
    }

    @PostMapping("/addData")
    public String addData() {
        return "addData......";
    }

    @PutMapping("/updateData")
    public String updateData() {
        return "updateData......";
    }

    @DeleteMapping("/delData")
    public String deleteData() {
        return "delData......";
    }

}
