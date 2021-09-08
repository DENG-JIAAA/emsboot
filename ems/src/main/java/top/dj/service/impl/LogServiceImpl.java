package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.Log;
import top.dj.POJO.DO.User;
import top.dj.mapper.LogMapper;
import top.dj.service.LogService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dj
 * @date 2021/4/27
 */

@Service
public class LogServiceImpl extends MyServiceImpl<LogMapper, Log> implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private EquApprovalServiceImpl equApprovalServiceImpl;

    @Override
    public IPage<Log> findLogPage(User user, Integer page, Integer limit) {
        if (superAdmin(user)) {
            return logMapper.selectPage(new Page<>(page, limit), null);
        }
        Integer nowRoomId = user.getUserRoom();
        Wrapper<Log> logWrapper = new QueryWrapper<>(new Log(null, nowRoomId));
        IPage<Log> selectPage = logMapper.selectPage(new Page<>(page, limit), logWrapper);
        Collections.reverse(selectPage.getRecords());
        return selectPage;
    }

    public boolean superAdmin(@Nullable User user) {
        if (user == null) return false;
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return roles.contains("ROLE_SUPERADMIN");
    }

    @Override
    public List<Log> getUserRecentLogs(User user, Integer num) {
        assert user != null;
        // 获取当前用户的所有日志数据
        List<Log> userLogs = getUserLogs(user.getLoginName());
        // 倒序
        Collections.reverse(userLogs);
        // 获取最新的num条数据
        int size = userLogs.size();
        return userLogs.subList(0, size < num ? size : num);
    }

    public List<Log> getUserLogs(String loginName) {
        List<Log> allLogs = logMapper.selectList(null);

        List<Log> userLogs = new ArrayList<>();
        for (Log oneLog : allLogs) {
            if (loginName.equals(oneLog.getOperater())) {
                userLogs.add(oneLog);
            }
        }
        return userLogs;
    }

}
