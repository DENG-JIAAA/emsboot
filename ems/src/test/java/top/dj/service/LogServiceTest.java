package top.dj.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Log;
import top.dj.POJO.DO.User;
import top.dj.service.impl.LogServiceImpl;

import java.util.List;

/**
 * @Author DengJia
 * @Date 2021/5/5
 * @Description:
 */

@SpringBootTest
public class LogServiceTest {
    @Autowired
    private LogServiceImpl logServiceImpl;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @Test
    void test01() {
        List<Log> userLogs = logServiceImpl.getUserLogs("sadmin6");
        for (Log userLog : userLogs) {
            System.out.println("userLog = " + userLog);
        }
    }

    @Test
    void test02() {
        User user = userService.getById(103);
        List<Log> userRecentLogs = logService.getUserRecentLogs(user, 5);
        for (Log log : userRecentLogs) {
            System.out.println("log = " + log);
        }
    }
}
