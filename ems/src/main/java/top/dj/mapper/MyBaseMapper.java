package top.dj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface MyBaseMapper<T> extends BaseMapper<T> {
}
