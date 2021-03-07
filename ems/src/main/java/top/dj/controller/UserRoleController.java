package top.dj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.UserRole;

/**
 * @author dj
 * @date 2021/2/28
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController extends BaseController<UserRole> {
}
