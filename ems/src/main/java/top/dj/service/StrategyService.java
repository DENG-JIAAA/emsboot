package top.dj.service;

import top.dj.POJO.DO.ScoreStrategy;

public interface StrategyService extends MyIService<ScoreStrategy> {
    ScoreStrategy getStrategy(Integer roomId);

    Boolean saveStrategy(Integer roomId, ScoreStrategy strategy);
}
