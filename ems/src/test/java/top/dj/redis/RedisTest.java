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
}

