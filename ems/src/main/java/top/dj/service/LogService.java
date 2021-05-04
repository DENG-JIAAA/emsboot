package top.dj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.dj.POJO.DO.Log;

public interface LogService extends MyIService<Log> {
    IPage<Log> findLogPage(Integer page, Integer limit);
}
