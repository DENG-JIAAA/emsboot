package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.AutoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.RoleForAuth;
import top.dj.POJO.DO.UserForAuth;
import top.dj.POJO.DO.UserRoleForAuth;

import java.util.List;

/**
 * @author dj
 * @date 2021/2/15
 */
@SpringBootTest
public class UserRoleForAuthTest {
    @Autowired
    private UserForAuthService userForAuthService;
    @Autowired
    private RoleForAuthService roleForAuthService;
    @Autowired
    private UserRoleForAuthService userRoleForAuthService;
    @Autowired
    private AutoMapper autoMapper;

    @Test
    void test01() {
//        UserForAuth userForAuth = new UserForAuth();
//        userForAuth.setId(1);
//        Wrapper<UserForAuth> wrapper = new QueryWrapper<>(userForAuth);
//        UserForAuth one = userForAuthService.getOne(wrapper);
//        System.out.println("getOne: " + one);

//        UserForAuth user = userForAuthService.getById(2);
//        System.out.println("user = " + user);
//        System.out.println("user.getAuthorities() = " + user.getAuthorities());

        UserForAuth userForAuth = new UserForAuth();
        userForAuth.setLoginName("djosimon");
        UserForAuth one = userForAuthService.getOne(new QueryWrapper<>(userForAuth));
        System.out.println("one.getAuthorities() = " + one.getAuthorities());
    }

    /*@Test
    void test02() {
        RoleForAuth role = roleForAuthService.findEntity(2);
        System.out.println(role);
    }*/

    @Test
    void test03() {
        UserRoleForAuth userRole = new UserRoleForAuth();
        userRole.setRoleId(3);
        Wrapper<UserRoleForAuth> wrapper = new QueryWrapper<>(userRole);
        List<UserRoleForAuth> list = userRoleForAuthService.list(wrapper);
        System.out.println(list);
    }

    @Test
    void test04() {
        // 想要 对关联属性的 关联属性进行自动加载
        RoleForAuth role = roleForAuthService.getById(3);
        List<UserForAuth> userList = role.getUserList();
        for (UserForAuth user : userList) {
            UserForAuth entity = autoMapper.mapperEntity(user);
            System.out.println(entity);
        }
    }

    @Test
    void test05() {
        // 想要 对关联属性的 关联属性进行自动加载
        UserForAuth user = new UserForAuth();
        user.setUserId(3);
        Wrapper<UserForAuth> wrapper = new QueryWrapper<>(user);
        UserForAuth one = userForAuthService.getOne(wrapper);
        List<RoleForAuth> userRoles = one.getUserRoles();
        for (RoleForAuth userRole : userRoles) {
            RoleForAuth role = autoMapper.mapperEntity(userRole);
            System.out.println(role);
        }
    }

    @Test
    void test06() {
        UserForAuth user = userForAuthService.getById(1);
        List<RoleForAuth> userRoles = user.getUserRoles();
        for (RoleForAuth userRole : userRoles) {
            String roleName = userRole.getRoleName();
            System.out.println(roleName);
        }
    }

    @Test
    void test07() {
        UserForAuth user = new UserForAuth();
        user.setLoginName("tom666");
        Wrapper<UserForAuth> wrapper = new QueryWrapper<>(user);
        UserForAuth one = userForAuthService.getOne(wrapper);
        System.out.println(one);
    }
}
