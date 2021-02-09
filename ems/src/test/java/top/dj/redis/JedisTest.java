package top.dj.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import top.dj.utils.RedisUtil;

/**
 * @author dj
 * @date 2021/2/7
 */
@SpringBootTest
public class JedisTest {
    Jedis jedis = null;
    @Autowired
    private RedisUtil redisUtil;

    /*@Before
    public void Before() {
        jedis = new Jedis();
    }

    @After
    public void After() {
        if (jedis != null) {
            jedis.close();
            jedis = null;
        }
    }*/

    /*@BeforeAll
    void BeforeAll() {
        jedis = new Jedis();
    }

    @AfterAll
    void AfterAll() {
        if (jedis != null) {
            jedis.close();
            jedis = null;
        }
    }*/

    /*@Test
    public void testString2() {

        jedis = new Jedis("192.168.1.130", 6379);
        jedis.auth("123456");

        // jedis.set("dj", "邓佳");
        System.out.println(jedis.get("\"e0b4dbf5-c207-442e-90b1-5160c9f32a20\""));
        System.out.println(jedis.get("dj"));

        if (jedis != null) {
            jedis.close();
        }
    }*/

    @Test
    void testJedis01() {
        redisUtil.set("2020-2021", "加油！", 1);
    }

    @Test
    void testJedis02() {
        System.out.println(redisUtil.exists("2020-2021"));
    }

    @Test
    void testJedis03() {
        Long dj = redisUtil.expire("dj", 1800, 0);
    }

    @Test
    void testJedis04() {
        System.out.println(redisUtil.getSet("hello", "world"));
    }

}
