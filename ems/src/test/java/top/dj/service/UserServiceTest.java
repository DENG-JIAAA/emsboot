package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.AutoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.Permission;
import top.dj.POJO.DO.Role;
import top.dj.POJO.DO.User;
import top.dj.component.MyGrantedAuthority;
import top.dj.mapper.EquipmentMapper;
import top.dj.mapper.UserMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author dj
 * @date 2021/2/28
 */
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private AutoMapper autoMapper;

    @Test
    void test01() {
        /*User user = new User();
        user.setId(209);
        user.setLoginName("测试多对多修改");
        boolean isSuccess = userService.updateById(user);
        user

        if (isSuccess) {
            userRoleService.getById()
        }*/
    }

    @Test
    void test02() {

        User user = new User();
        user.setId(217);
        user.setLoginName("ourEarth");
        //boolean update = userService.update(new QueryWrapper<>(user));
        boolean update = userService.updateById(user);
        System.out.println("update = " + update);
    }

    @Test
    void test03() {
        boolean b = userService.removeById(3333);
        System.out.println("b = " + b);
    }

    @Test
    void test04() {
        User user = new User();
        user.setLoginName("mingren");
        user.setRealName("鸣人");
        user.setLoginPwd("654321a");

        boolean save = userService.save(user);

        if (save) {
            User one = userService.getOne(new QueryWrapper<>(user));
            System.out.println("one = " + one);
        }
    }

    @Test
    void test05() {
        User user = new User();
        user.setId(226);
        user.setLoginName("aaaaaa");
        user.setLoginPwd("654321a");
        user.setRealName("aaaaa");
        User savedUser = userService.getOne(new QueryWrapper<>(user));
        System.out.println("savedUser = " + savedUser);
    }

    @Test
    void test06() {
        /*User user = new User(1);
        Wrapper<User> wrapper = new QueryWrapper<>(user);

        User oneByService = userService.getOne(wrapper);
        System.out.println("oneByService = " + oneByService);*/

        User byId = userService.getById(1);
        System.out.println("byId = " + byId);

        /*User oneByMapper = userMapper.selectOne(wrapper);
        System.out.println("oneByMapper = " + oneByMapper);*/

    }

    @Test
    void test07() {
        User user = userService.getById(1);
        Collection<? extends GrantedAuthority> roles = user.getAuthorities();
        Collection<GrantedAuthority> allAuth = new HashSet<>();
        for (GrantedAuthority role : roles) {
            allAuth.add(new SimpleGrantedAuthority(role.getAuthority()));
            Role oneRole = roleService.getOne(new QueryWrapper<>(new Role(role.getAuthority())));
            List<Permission> permissions = oneRole.getRolePers();
            System.out.println("oneRole = " + oneRole);
            for (Permission per : permissions) {
                if (per != null && per.getAuthority() != null) {
                    GrantedAuthority grantedAuthority = new MyGrantedAuthority(per.getPerUrl(), per.getPerMethod());
                    allAuth.add(grantedAuthority);
                }
            }
        }
        for (GrantedAuthority authority : allAuth) {
            System.out.println("authority.getAuthority() = " + authority.getAuthority());
        }

        System.out.println("allAuth = " + allAuth);
    }

    @Test
    void test08() {
        System.out.println("userService.getById(101) = " + userService.getById(101));
        System.out.println("userService.getById(102) = " + userService.getById(102));
        System.out.println("userService.getById(103) = " + userService.getById(103));
    }

    @Test
    void test09() {
    }

    @Test
    void test10() {
        User user = new User();
        user.setUserRoom(3);
        System.out.println("实践室C所有管理员---------------------------------->>");
        List<User> admins = userMapper.selectList(new QueryWrapper<>(user));
        for (User admin : admins) {
            System.out.println("admin = " + admin);
        }

        Equipment equipment = new Equipment();
        equipment.setEquRoom(3);
        System.out.println("实践室C所有的设备---------------------------------->>");
        List<Equipment> equs = equipmentMapper.selectList(new QueryWrapper<>(equipment));
        for (Equipment equ : equs) {
            System.out.println("equ = " + equ);
        }
    }

    @Test
    void test11() {
        Role role = new Role(1);

        Wrapper<Role> wrapper = new QueryWrapper<>(role);
        Role one = roleService.getOne(wrapper);

        List<User> users = one.getRoleUsers();
        for (User user : users) {
            User entity = autoMapper.mapperEntity(user);
            System.out.println("entity = " + entity);
        }
    }

    @Test
    void test12() {
        int i = 70;
        User user = new User();
        user.setId(70);
        user.setLoginPwd("pwd19b");
        User one = userMapper.selectOne(new QueryWrapper<>(user));
        System.out.println("one = " + one);
    }
}
