package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.AutoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Role;
import top.dj.POJO.DO.User;
import top.dj.POJO.DO.UserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dj
 * @date 2021/2/22
 */
@SpringBootTest
public class UserRoleTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AutoMapper autoMapper;
    @Autowired
    private MyIService<User> userMyIService;
    @Autowired
    private MyIService<Role> roleMyIService;

    @Test
    void test() {
        System.out.println("userMyIService.getById(1) = " + userMyIService.getById(1));
        System.out.println("roleMyIService.getById(1) = " + roleMyIService.getById(1));
    }

    @Test
    void test01() {
        User user = new User();
        user.setLoginName("jerry666");
        User one = userService.getOne(new QueryWrapper<>(user));
        System.out.println("one = " + one);
        // System.out.println("one.getAuthorities() = " + one.getAuthorities());
    }

    @Test
    void test02() {
        // 想要 对关联属性的 关联属性进行自动加载
        Role role = roleService.getById(2);
        List<User> userList = role.getRoleUsers();
        for (User user : userList) {
            User entity = autoMapper.mapperEntity(user);
            System.out.println("entity = " + entity);
        }
    }

    @Test
    void test03() {
//        Role role = roleService.getById(3);
//        System.out.println("role = " + role);

        Role role1 = new Role();
        role1.setRoleName("ROLE_ADMIN");
        Role one = roleService.getOne(new QueryWrapper<>(role1));
        System.out.println("one = " + one);
    }

    /*@Test
    void test04() {
        // 想要 对关联属性的 关联属性进行自动加载
        User tom = userService.getById(1);
        List<Role> userRoles = tom.getUserRoles();
        for (Role userRole : userRoles) {
            Role role = autoMapper.mapperEntity(userRole);
            System.out.println("tom的role = " + role);
        }
    }*/

    @Test
    void test05() {
        List<Integer> updateIDs = new ArrayList<>();
        updateIDs.add(1);
        updateIDs.add(2);

        UserRole userRole = new UserRole();
        userRole.setUserId(218);
        boolean removeAll = userRoleService.remove(new QueryWrapper<>(userRole));
        if (removeAll) {
            UserRole ur = new UserRole();
            for (Integer updateID : updateIDs) {
                ur.setUserId(218);
                ur.setRoleId(updateID);
                userRoleService.save(ur);
            }
        }
    }

    @Test
    void test06() {
        List<String> rolesList = new ArrayList<>();
        rolesList.add("ROLE_VISITOR");
        rolesList.add("ROLE_SUPERADMIN");

        for (String s : rolesList) {
            Role role = new Role();
            role.setRoleName(s);
            Role oneRole = roleService.getOne(new QueryWrapper<>(role));
            System.out.println("oneRole.getId() = " + oneRole.getId());
        }
    }

    @Test
    void test07() {

        List<UserRole> userRoles = new ArrayList<>();

        userRoles.add(new UserRole(null, 5, 1));
        userRoles.add(new UserRole(null, 5, 2));
        userRoles.add(new UserRole(null, 5, 3));

        System.out.println("userRoleService.saveBatch(userRoles) = " + userRoleService.saveBatch(userRoles));
    }

}
