package top.dj.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Role;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.impl.RoleServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @author dj
 * @date 2021/2/23
 */
@SpringBootTest
public class RoleTest {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Test
    void test() {
        Role role = roleService.getById(2);
        System.out.println("role = " + role);
    }

    @Test
    void test01() {
        Role role = roleService.getById(1);
        ResultVO<Role> roleResultVO = new ResultVO<>(200, "ok", role);
        System.out.println("roleResultVO = " + roleResultVO);
    }

    @Test
    void test02() {
        System.out.println("roleService.getById(1) = " + roleService.getById(1));
        System.out.println("roleService.getById(2) = " + roleService.getById(2));
    }

    @Test
    void test03() {
        Map<String, List<Integer>> roleUserIds = roleServiceImpl.getRoleUserIds();
        System.out.println("roleUserIds = " + roleUserIds);
    }
}
