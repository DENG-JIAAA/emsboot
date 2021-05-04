package top.dj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.Log;
import top.dj.mapper.LogMapper;
import top.dj.service.LogService;

/**
 * @author dj
 * @date 2021/4/27
 */

@Service
public class LogServiceImpl extends MyServiceImpl<LogMapper, Log> implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public IPage<Log> findLogPage(Integer page, Integer limit) {
        return logMapper.selectPage(new Page<>(page, limit), null);
    }

}
