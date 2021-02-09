package top.dj.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.dj.POJO.DO.User;

/**
 * @author dj
 * @date 2021/2/1
 */
@SpringBootTest
public class RedisTest {
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
}

