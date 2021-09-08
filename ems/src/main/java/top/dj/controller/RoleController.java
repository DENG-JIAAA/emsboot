package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.Role;
import top.dj.POJO.VO.ResultVO;
import top.dj.POJO.VO.RoleVO;
import top.dj.service.RoleService;

import java.util.List;
import java.util.Map;

/**
 * @author dj
 * @date 2021/1/13
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {
    @Autowired
    private RoleService roleService;

    @GetMapping("/vo/all")
    public ResultVO<List<RoleVO>> getAllVORoles() {
        List<RoleVO> roleVOS = roleService.getRoleVOS();
        boolean OK = roleVOS != null && !roleVOS.isEmpty();
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取角色列表成功" : "获取角色列表失败", roleVOS);
    }

    @GetMapping("/map")
    public ResultVO<Map<Integer, String>> getTheMapOfRole() {
        Map<Integer, String> map = roleService.getTheMapOfRole();
        return new ResultVO<>(20000, "角色分布情况", map);
    }

}
