package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import top.dj.POJO.DO.Permission;
import top.dj.POJO.DO.Role;
import top.dj.mapper.PermissionMapper;

import java.util.*;

/**
 * @author dj
 * @date 2021/3/2
 */

@SpringBootTest
public class PermissionServiceTest {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @Test
    void test01() {
        HashMap<String, Collection<ConfigAttribute>> map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<Permission> permissions = permissionMapper.selectList(null);
        for (Permission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getPerName());
            // 此处只添加了权限的名字，其实还可以添加更多权限的信息，
            // 例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            // 用权限的getPerUrl() 作为map的key，用 ConfigAttribute 的集合作为 value，
            map.put(permission.getPerUrl(), array);
        }

        for (String s : map.keySet()) {
            System.out.println("s = " + s);
            System.out.println("map.get(s) = " + map.get(s));
        }

    }

    @Test
    void test02() {
        Permission permission = new Permission(5);
        System.out.println("permissionService.getById(5) = " + permissionService.getById(5));
        Permission one = permissionService.getOne(new QueryWrapper<>(permission));
        System.out.println("one = " + one);
    }

    @Test
    void test03() {
        List<Permission> permissions = permissionMapper.selectList(null);
        for (Permission permission : permissions) {
            System.out.println("permission.getAuthority() = " + permission.getAuthority());
        }
    }

    @Test
    void test04() {
    }

    @Test
    void test05() {
        Role role = roleService.getById(3);
        List<Permission> permissions = role.getRolePers();

        Set<Integer> pIds = new HashSet<>();
        for (Permission permission : permissions) {
            Integer id = permission.getpId();
            if (id != null) {
                pIds.add(id);
            }
        }

        for (Integer pId : pIds) {

        }
    }
}
