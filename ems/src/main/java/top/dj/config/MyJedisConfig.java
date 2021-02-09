package top.dj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author dj
 * @date 2021/2/7
 */
@Configuration
@Slf4j
public class MyJedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * redis -- 逻辑库选择
     */
    @Value("${spring.redis.database}")
    private int database;

    /**
     * redis -- 连接池最小空闲
     */
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    /**
     * redis -- 连接池最大空闲
     */
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    /**
     * redis -- 连接池最大连接数
     */
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    /**
     * redis -- 连接池最大等待时间
     */
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWait;

    /**
     * redis -- 连接池超时时间
     */
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Bean
    public JedisPool redisPoolFactory() {
        log.info("JedisPool注入成功！");
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        return new JedisPool(jedisPoolConfig, host, port, timeout, password);
    }
}
