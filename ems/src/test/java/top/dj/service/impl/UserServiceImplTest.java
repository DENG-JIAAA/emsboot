package top.dj.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.User;
import top.dj.service.UserService;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testAdd() {
        Random random = new Random();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //User user = new User(null, 3, "wangwu", "654321", "王五", 1, "15639839855", "ww@ww.com", 3, timestamp, timestamp, timestamp, 1, null, null);
        User user = new User();
        for (int i = 0; i < 200; i++) {
            user.setUserRoom(random.nextInt(3) + 1);
            String str = UUID.randomUUID().toString();
            user.setLoginName(str.substring(0, 6) + i);
            user.setLoginPwd("pwd" + str.substring(0, 3));
            user.setRealName(str.substring(0, 6).toUpperCase());
            user.setUserSex(random.nextInt(3) + 1);
            user.setUserPhone((new Random().nextInt(9) + 1) * 10000000000L + i + "");
            user.setUserEmail(str.substring(0, 3) + "@" + str.substring(0, 3).toUpperCase() + ".com");
            user.setUserRole(random.nextInt(3) + 1);
            user.setCreateTime(timestamp);
            user.setLoginTime(timestamp);
            user.setLastLoginTime(timestamp);
            user.setLoginCount(1);
            userService.addOneEntity(user);
        }
    }

    @Test
    void testFindOne() {
        System.out.println(userService.findEntity(1));
    }

    /*@Test
    void testFindOne2() {
        System.out.println(userService.findWrapperUser(1, 2));
    }

    @Test
    void testFindOne3() {
        System.out.println(userService.findWrapperUser(1));
    }*/

    /*@Test
    void testFindOne4() {
        System.out.println(userServiceImpl.findWrapperUser2(1));
    }

    @Test
    void testFindOne5() {
        List<UserVO> data = userServiceImpl.findWrapperUser2(2, 5).getData();
        data.forEach(System.out::println);
    }*/

    @Test
    void findUserVOByLoginName() {
        System.out.println(userService.findUserVO("b4431b99"));
    }

}
