package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.ScoreStrategy;
import top.dj.mapper.StrategyMapper;
import top.dj.service.RoomService;
import top.dj.service.StrategyService;

/**
 * @Author DengJia
 * @Date 2021/5/3
 * @Description:
 */

@Service
public class StrategyServiceImpl extends MyServiceImpl<StrategyMapper, ScoreStrategy> implements StrategyService {
    @Autowired
    private RoomService roomService;
    @Autowired
    private StrategyMapper strategyMapper;
    @Autowired
    private StrategyService strategyService;

    @Override
    public ScoreStrategy getStrategy(Integer roomId) {
        ScoreStrategy strategy = new ScoreStrategy(null, roomId);
        return strategyMapper.selectOne(new QueryWrapper<>(strategy));
    }

    @Override
    public Boolean saveStrategy(Integer roomId, ScoreStrategy strategy) {
        // 更新或保存 根据实践室roomId作为条件，来更新积分策略，更新的信息为：strategy
        strategy.setRoomId(roomId);
        return strategyService.saveOrUpdate(strategy, new QueryWrapper<>(new ScoreStrategy(null, roomId)));
    }
}
