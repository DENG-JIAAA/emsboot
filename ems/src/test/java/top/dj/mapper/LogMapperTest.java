package top.dj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Log;
import top.dj.service.LogService;

import java.util.List;

/**
 * @author dj
 * @date 2021/4/29
 */

@SpringBootTest
public class LogMapperTest {
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private LogService logService;

    @Test
    void test01() {
        /*IPage<Log> logPage = logService.page(new Page<>(1, 9), null);
        List<Log> records = logPage.getRecords();
        for (Log record : records) {
            System.out.println("record = " + record);
        }*/

        Page<Log> logPage = logMapper.selectPage(new Page<>(1, 5), null);
        List<Log> records = logPage.getRecords();
        for (Log record : records) {

            System.out.println("record = " + record);
        }

    }


}
