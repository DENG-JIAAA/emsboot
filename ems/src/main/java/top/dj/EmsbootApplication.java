package top.dj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("top.dj.mapper")
public class EmsbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsbootApplication.class, args);
    }

}
