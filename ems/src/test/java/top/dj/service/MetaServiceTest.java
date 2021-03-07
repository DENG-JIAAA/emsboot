package top.dj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.mapper.MetaMapper;

/**
 * @author dj
 * @date 2021/3/6
 */
@SpringBootTest
public class MetaServiceTest {
    @Autowired
    private MetaMapper metaMapper;

}
