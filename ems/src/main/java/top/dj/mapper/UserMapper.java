package top.dj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.Primary;
import top.dj.POJO.DO.User;

@Primary
public interface UserMapper extends BaseMapper<User> {
}
