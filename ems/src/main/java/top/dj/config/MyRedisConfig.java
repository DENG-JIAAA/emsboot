package top.dj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import top.dj.POJO.DO.User;

/**
 * @author dj
 * @date 2021/2/2
 */
@Configuration
public class MyRedisConfig {
    @Bean
    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(User.class));
        return template;
    }
}
