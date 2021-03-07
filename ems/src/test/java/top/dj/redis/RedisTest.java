package top.dj.redis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.dj.POJO.DO.User;
import top.dj.mapper.UserMapper;
import top.dj.service.UserService;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author dj
 * @date 2021/2/1
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;

    // @Autowired
    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Test
    void test01() {
        stringRedisTemplate.opsForValue().append("dj", "邓佳");
    }

    @Test
    void getEntityByToken() {
        System.out.println(redisTemplate.getExpire("268fc1c2-34f1-426a-b496-5056c66da1ee"));
        System.out.println(redisTemplate.getExpire("268fc1c2-34f1-426a-b496"));
    }

    @Test
    void testGet() {
        System.out.println(redisTemplate.opsForValue().get("adsafsafa"));
    }

    @Test
    void Endurance() {
        User user = new User();
        user.setId(1);
        Wrapper<User> wrapper = new QueryWrapper<>(user);
        User one = userMapper.selectOne(wrapper);
        redisTemplate.opsForValue().set("djosimon-token", one);


//        System.out.println(StringUtils.hasText("djosimon-token"));
//        User user = redisTemplate.opsForValue().get("djosimon-token");
//        System.out.println(user);

//        String hello = stringRedisTemplate.opsForValue().get("hello");
//        System.out.println(hello);
    }

    @Test
    void test02() {
        // redisTemplate.setValueSerializer(new StringRedisSerializer());
        /*String token = "e840b7f2-4551-4e83-a05d-78c856a09eca";

        System.out.println("直接输出 = " + redisTemplate.opsForValue().get(token));
        User user2 = (User) redisTemplate.opsForValue().get(token);
        System.out.println("转换输出 = " + user2);

        String hello = stringRedisTemplate.opsForValue().get("hello");
        System.out.println(hello);*/

        User user = new User();
        user.setLoginName("小明");
        user.setLoginPwd("xiaoming");
        redisTemplate.opsForValue().set("token", user, Duration.ofMinutes(30L));
        User redisUser = (User) redisTemplate.opsForValue().get("user");
        System.out.println("redisUser = " + redisUser);
    }

    @Test
    void test03() {
        User user = (User) redisTemplate.opsForValue().get("token");
        System.out.println("user = " + user);
    }

    @Test
    void test04() {
        User user = new User();
        user.setLoginName("djosimon");
        User one = userService.getOne(new QueryWrapper<>(user));
        System.out.println("multiUser --> " + one);

        redisTemplate.opsForValue().set("oneUser", one, Duration.ofDays(1L));
        System.out.println("redisJson --> " + redisTemplate.opsForValue().get("oneUser"));
        User redisUser = redisTemplate.opsForValue().get("oneUser");
        System.out.println("redisUser --> " + redisUser);
//        System.out.println("redisUser.getAuthorities() = " + redisUser.getAuthorities());
    }

    @Test
    void test05() {
        // 在获取value前应该把值设置为序列化
        //redisTemplate.setValueSerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));

        String token = "8a24cc6c-1fc4-478c-8e99-70aaaa809251";
        User user = redisTemplate.opsForValue().get(token);
        System.out.println("user = " + user);
    }
}
