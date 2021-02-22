package top.dj.config;

import com.github.dreamyoung.mprelation.AutoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dj
 * @date 2021/2/15
 */
@Configuration
// 扫描被注解的实体类
public class AutoMapperConfig {
    @Bean
    public AutoMapper autoMapper() {
        return new AutoMapper(new String[]{"top.dj.POJO.DO"}); //配置实体类所在目录（可多个）
    }

}
