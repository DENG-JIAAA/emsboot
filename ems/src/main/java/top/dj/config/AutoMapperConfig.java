package top.dj.config;

import com.github.dreamyoung.mprelation.AutoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主要是扫描被注解的实体类
 *
 * @author dj
 * @date 2021/2/15
 */
@Configuration
public class AutoMapperConfig {
    @Bean
    public AutoMapper autoMapper() {
        // 配置实体类所在目录（可多个）
        return new AutoMapper(new String[]{"top.dj.POJO.DO"});
    }

}
