package top.dj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.Role;

/**
 * @author dj
 * @date 2021/1/13
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {

}
