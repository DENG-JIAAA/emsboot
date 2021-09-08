package top.dj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.dj.POJO.DO.Log;
import top.dj.POJO.DO.User;

import java.util.List;

public interface LogService extends MyIService<Log> {
    IPage<Log> findLogPage(User user, Integer page, Integer limit);

    List<Log> getUserRecentLogs(User user, Integer num);
}
