package top.dj.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.ScoreStrategy;
import top.dj.service.StrategyService;

/**
 * @Author DengJia
 * @Date 2021/5/3
 * @Description:
 */
@SpringBootTest
public class StrategyMapperServiceTest {
    @Autowired
    private StrategyService strategyService;
    @Autowired
    private StrategyMapper strategyMapper;

    @Test
    void test01() {
        /*ScoreStrategy strategy = new ScoreStrategy(null, true, true, false, 5, 2, 10, 8, 5, 3, 2, 1);
        strategyMapper.insert(strategy);*/
    }

    @Test
    void test02() {
        ScoreStrategy strategy = strategyMapper.selectById(2);
        strategy.setIsNums(false);
        strategy.setIsQuantity(true);

        strategyMapper.update(strategy, new QueryWrapper<>(new ScoreStrategy(null, 1)));
    }
}
