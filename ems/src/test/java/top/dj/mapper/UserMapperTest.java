package top.dj.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.LoginVO;
import top.dj.service.UserService;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    void test01() {
        User user = new User();
        user.setId(217);
        user.setLoginName("myEarth");

        int id = userMapper.updateById(user);
        System.out.println("id = " + id);
    }

    @Test
    void test02() {
        LoginVO loginVO = new LoginVO("ad0a2492", "pwdad0");
        User user = new User();
        BeanUtils.copyProperties(loginVO, user);

        User selectOne = userMapper.selectOne(new QueryWrapper<>(user));
        System.out.println("【mapper】 selectOne = " + selectOne);

        User serviceOne = userService.getOne(new QueryWrapper<>(user));
        System.out.println("【service】 selectOne = " + serviceOne);
    }

}
