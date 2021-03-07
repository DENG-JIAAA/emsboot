package top.dj.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.dj.POJO.DO.User;

import java.time.Duration;

/**
 * @author dj
 * @date 2021/2/2
 */
@Configuration
public class MyRedisConfig {
    @Autowired
    @Qualifier(value = "myGrantedAuthorityDeserializer")
    private StdDeserializer<SimpleGrantedAuthority> myGrantedAuthorityDeserializer;
    /*@Autowired
    private ObjectMapper objectMapper;*/

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置 redis 默认序列化器
        // 说明：jackson + redis 序列化会根据 get方法 自动序列没有此字段的序列化字段出来
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(User.class));

        //ObjectMapper objectMapper = new ObjectMapper();
        // 设置 redis 反序列化器
        objectMapper().registerModule(new SimpleModule()
                .addDeserializer(SimpleGrantedAuthority.class, myGrantedAuthorityDeserializer));

        return template;
    }

    /**
     * 自定义通用Redis缓存管理器
     * RedisCacheManager（GenericJackson2JsonRedisSerializer指定转换全局对象）
     */
    @Bean
    @Primary
    public CacheManager genericCacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofHours(12L))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }

    /**
     * 自定义特定Redis缓存管理器
     * RedisCacheManager（Jackson2JsonRedisSerializer指定转换单一对象）
     */
    @Bean
    public CacheManager userCacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofDays(1L))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new Jackson2JsonRedisSerializer<>(User.class)));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();

    }
}
