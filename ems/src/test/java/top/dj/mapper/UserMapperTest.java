//package top.dj.mapper;
//
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import top.dj.pojo.DO.User;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class UserMapperTest {
//    @Autowired
//    private UserMapper userMapper;
//    @Test
//    void testSelect() {
//        User user = new User();
//        user.setUserId(1);
//        Wrapper<User> wrapper = new QueryWrapper<>(user);
//        System.out.println(userMapper.selectOne(wrapper));
//    }
//
//}
