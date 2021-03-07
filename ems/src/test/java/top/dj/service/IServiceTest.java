package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Role;
import top.dj.POJO.DO.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dj
 * @date 2021/2/23
 */
@SpringBootTest
public class IServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MyIService<User> userIService;
    @Autowired
    private MyIService<Role> roleIService;
//    @Autowired
//    private BaseService<User> baseService;

    /*@Test
    void test() {
        User user = userService.getById(1);
        System.out.println(user);
    }*/

    @Test
    void test01() {
        System.out.println("userIService.getById(1) = " + userIService.getById(1));
        System.out.println("roleIService.getById(1) = " + roleIService.getById(1));
    }

    @Test
    void test02() {
        IPage<User> userPage = new Page<>(1, 3);
        IPage<User> iPage = userIService.page(userPage);
        List<User> records = iPage.getRecords();
        for (User record : records) {
            System.out.println("record = " + record);
        }
    }

    @Test
    void test03() {
        IPage<User> iPage = new Page<>(1, 3);
        IPage<User> userPage = userService.page(iPage);

        List<User> records = userPage.getRecords();
        List<User> retUsers = new ArrayList<>();
        for (User record : records) {
            User one = userService.getOne(new QueryWrapper<>(record));
            retUsers.add(one);
        }
        System.out.println(userPage.setRecords(retUsers).getRecords());
    }

    @Test
    void test04() {
        IPage<Role> iPage = new Page<>(1, 3);
        IPage<Role> rolePage = roleIService.page(iPage);

        List<Role> records = rolePage.getRecords();
        List<Role> retRoles = new ArrayList<>();
        for (Role record : records) {
            Role one = roleIService.getOne(new QueryWrapper<>(record));
            // Role one = roleService.getOne(new QueryWrapper<>(record));
            retRoles.add(one);
        }
        System.out.println(rolePage.setRecords(retRoles).getRecords());
    }

    /*@Test
    void test04() {
        IPage<Role> iPage = new Page<>(1, 3);
        IPage<Role> rolePage = roleIService.page(iPage);

        List<Role> records = rolePage.getRecords();
        List<Role> retRoles = new ArrayList<>();
        for (Role record : records) {
            Role one = roleService.getOne(new QueryWrapper<>(record));
            retRoles.add(one);
        }
        System.out.println(rolePage.setRecords(retRoles).getRecords());
    }*/

    @Test
    void test05() {
        User user = new User();
        user.setLoginName("djosimon");
        user = userService.getOne(new QueryWrapper<>(user));
        System.out.println(user);
    }

}
