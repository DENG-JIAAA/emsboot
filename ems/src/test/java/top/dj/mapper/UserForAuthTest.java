//package top.dj.mapper;
//
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author dj
// * @date 2021/2/14
// */
//@SpringBootTest
//public class UserForAuthTest {
//    @Autowired
//    private UserForAuthMapper userForAuthMapper;
//
//    @Test
//    void test01() {
//        UserForAuth user = new UserForAuth();
//        List<RoleForAuth> list = new ArrayList<>();
//        RoleForAuth role = new RoleForAuth();
//        role.setRoleName("TEACHER");
//        list.add(role);
//        user.setUserRoles(list);
//        userForAuthMapper.insert(user);
//    }
//
//    @Test
//    void test02() {
//        UserForAuth user = new UserForAuth();
////        user.setUserId(1);
//        Wrapper<UserForAuth> wrapper = new QueryWrapper<>(user);
//        UserForAuth one = userForAuthMapper.selectOne(wrapper);
//        System.out.println(one);
////        System.out.println(one.getAuthorities());
//    }
//
//    @Test
//    void test03() {
//        UserForAuth user = new UserForAuth();
//        user.setLoginName("tom666");
//
//        Wrapper<UserForAuth> wrapper = new QueryWrapper<>(user);
//        UserForAuth one = userForAuthMapper.selectOne(wrapper);
//        System.out.println(one);
//    }
//}
