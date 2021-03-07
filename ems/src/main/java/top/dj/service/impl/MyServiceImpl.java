package top.dj.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.dreamyoung.mprelation.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import top.dj.mapper.MyBaseMapper;
import top.dj.service.MyIService;

/**
 * @author dj
 * @date 2021/2/23
 */
@Service
@Primary
public class MyServiceImpl<M extends MyBaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T> implements MyIService<T> {
}
