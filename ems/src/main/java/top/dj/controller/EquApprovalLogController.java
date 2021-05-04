package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.ScoreStrategy;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.EquApprovalLogService;
import top.dj.service.StrategyService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author dj
 * @date 2021/3/17
 */

@RestController
@RequestMapping("/equApprovalLog")
public class EquApprovalLogController {
    @Autowired
    private EquApprovalLogService equApprovalLogService;
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private StrategyService strategyService;


    /**
     * 获取当前用户所在设备库的所有设备被借使用的次数。（数据库一条记录算一次）
     * 根据申请log表的origin_id判定，相同的origin_id被看作是一条申请记录
     *
     * @param request 包含用户token信息
     * @return
     */
    @GetMapping("/totalNum")
    public ResultVO<Integer> getTheNumOfBorrow(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        assert user != null;
        Set<Integer> appIds = equApprovalLogService.numOfBorrow(user.getId());
        return new ResultVO<>(20000, "次数", appIds.size());
    }

    /**
     * 获取当前用户所在设备库总的 被借用设备的数量
     *
     * @param request 包含用户token信息
     * @return
     */
    @GetMapping("/totalQuantity")
    public ResultVO<Integer> getTheQuantityOfBorrow(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        assert user != null;
        Integer quantity = equApprovalLogService.quantityOfBorrow(user.getId());
        return new ResultVO<>(20000, "数量", quantity);
    }

    /**
     * 获取当前设备库所有设备的使用方向，再根据查数据库，计算所有的些方向中，哪些要纳入计分，哪些不用，计分的权重是多少。
     *
     * @param request 包含用户token信息
     * @return
     */
    @GetMapping("/useCateScore")
    public ResultVO<Integer> getTheUseCateScore(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        assert user != null;
        // useCateScore -- 根据数据库响应实践室策略计算出来的 使用方向得分
        Integer useCateScore = equApprovalLogService.useCateScore(user.getId());
        return new ResultVO<>(20000, "方向得分", useCateScore);
    }

    /**
     * 获取计分策略
     *
     * @param request
     * @return
     */
    @GetMapping("/strategy")
    public ResultVO<ScoreStrategy> getStrategy(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        assert user != null;
        ScoreStrategy strategy = strategyService.getStrategy(user.getUserRoom());
        return new ResultVO<>(20000, "获取当前实践室的计分策略", strategy);
    }

    /**
     * 保存积分策略
     *
     * @param request
     * @return
     */
    @PostMapping("/strategy")
    public ResultVO<Boolean> saveStrategy(HttpServletRequest request,
                                          @RequestBody ScoreStrategy strategy) {

        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        assert user != null;
        Boolean save = strategyService.saveStrategy(user.getUserRoom(), strategy);
        return new ResultVO<>(20000, "保存实践室的计分策略", save);
    }


}
