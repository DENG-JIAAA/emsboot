package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.DO.EquApprovalLog;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.ScoreStrategy;
import top.dj.mapper.EquApprovalLogMapper;
import top.dj.service.EquApprovalLogService;
import top.dj.service.EquipmentService;
import top.dj.service.StrategyService;
import top.dj.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author dj
 * @date 2021/3/12
 */

@Service
public class EquApprovalLogServiceImpl extends MyServiceImpl<EquApprovalLogMapper, EquApprovalLog> implements EquApprovalLogService {
    @Autowired
    private EquApprovalLogMapper equApprovalLogMapper;
    @Autowired
    private EquApprovalLogService equApprovalLogService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private StrategyService strategyService;

    /**
     * 将用户借用设备的信息记录在册（入库了多少以及报废了多少），维修也是为了入库和报废
     *
     * @param equApproval 用户归还的那条申请 源纪录
     * @return
     */
    @Override
    public boolean recordLog(EquApproval equApproval) {
        EquApprovalLog log = new EquApprovalLog();
        log.setOriginId(equApproval.getId());
        // 入库的log日志statusId=1 报废=2
        boolean store = equApproval.getApprovalStatusId().equals(1);
        log.setApprovalStatusId(store ? 1 : 2);
        EquApprovalLog one = equApprovalLogService.getOne(new QueryWrapper<>(log));
        if (one == null) {
            copyProperties(equApproval, log);
            return equApprovalLogService.save(log);
        }
        one.setEquQuantity(one.getEquQuantity() + equApproval.getEquQuantity());
        return equApprovalLogService.updateById(one);
    }

    @Override
    public Set<Integer> numOfBorrow(Integer userId) {
        // 获取当前库日志
        List<EquApprovalLog> logList = getAllLogs(userId);
        Set<Integer> appIds = new HashSet<>();
        // 获取所有设备被申请的 申请日志id
        logList.forEach(oneLog -> appIds.add(oneLog.getOriginId()));
        return appIds;
    }

    @Override
    public Integer quantityOfBorrow(Integer userId) {
        List<EquApprovalLog> logList = getAllLogs(userId);
        Integer totalQuantity = 0;
        for (EquApprovalLog oneLog : logList) {
            totalQuantity += oneLog.getEquQuantity();
        }
        return totalQuantity;
    }

    @Override
    public Integer useCateScore(Integer userId) {
        // appIds -- 所有的借用记录id
        Set<Integer> appIds = numOfBorrow(userId);
        // ucIds -- useCateIds 集合
        List<Integer> ucIds = new ArrayList<>();
        for (Integer appId : appIds) {
            // log借用日志表里面 有入库和报废的信息，其实两者的来源相同，只需要处理一条记录即可。
            Wrapper<EquApprovalLog> wrapper = new QueryWrapper<>(new EquApprovalLog(null, null, appId));
            List<EquApprovalLog> list = equApprovalLogService.list(wrapper);

            Integer ucId = list.get(0).getEquUseCate();
            // 将ucId存放起来，要求ucId可以重复，代表的是不同的借用，只是使用的方向相同。
            ucIds.add(ucId);
        }
        // 根据对应实践室的策略 计算得分
        Integer roomId = userService.getById(userId).getUserRoom();
        return calcScore(roomId, ucIds);
    }

    /**
     * @param roomId 当前实践室
     * @param ucIds  借用设备的使用方向id集合，多条相同的id代表多次不同的借用，相同的方向。
     * @return 根据 ems_strategy 数据表的策略计算总分进行返回。
     */
    private Integer calcScore(Integer roomId, List<Integer> ucIds) {
        int totalScore = 0;
        Wrapper<ScoreStrategy> wrapper = new QueryWrapper<>(new ScoreStrategy(null, roomId));
        ScoreStrategy strategy = strategyService.getOne(wrapper);

        Boolean useCate = strategy.getIsUseCate();
        // 设备使用方向不纳入绩效得分选项，直接返回0
        if (!useCate) return 0;

        int[] times = cateTimes(ucIds);

        Boolean keYan = strategy.getShowKeYan();
        Boolean biSai = strategy.getShowBiSai();
        Boolean shouKe = strategy.getShowShouKe();
        Boolean geRen = strategy.getShowGeRen();
        Boolean xiaoWai = strategy.getShowXiaoWai();
        Boolean qiTa = strategy.getShowQiTa();

        if (keYan) totalScore += strategy.getKeYanWeight() * times[0];
        if (biSai) totalScore += strategy.getBiSaiWeight() * times[1];
        if (shouKe) totalScore += strategy.getShouKeWeight() * times[2];
        if (geRen) totalScore += strategy.getGeRenWeight() * times[3];
        if (xiaoWai) totalScore += strategy.getXiaoWaiWeight() * times[4];
        if (qiTa) totalScore += strategy.getQiTaWeight() * times[5];

        return totalScore;
    }

    private int[] cateTimes(List<Integer> ucIds) {
        int n1 = 0, n2 = 0, n3 = 0, n4 = 0, n5 = 0, n6 = 0;
        for (Integer ucId : ucIds) {
            switch (ucId) {
                case 1:
                    ++n1;
                    break;
                case 2:
                    ++n2;
                    break;
                case 3:
                    ++n3;
                    break;
                case 4:
                    ++n4;
                    break;
                case 5:
                    ++n5;
                    break;
                case 6:
                    ++n6;
                    break;
            }
        }
        return new int[]{n1, n2, n3, n4, n5, n6};
    }

    /**
     * 获取当前用户负责设备库的所有申请记录日志
     *
     * @param userId 当前库的管理员id
     * @return 当前设备库的申请记录
     */
    private List<EquApprovalLog> getAllLogs(Integer userId) {
        // eIds -- 当前设备库的所有设备id
        List<Integer> eIds = oneRoomEquIds(userService.getById(userId).getUserRoom());
        List<Integer> lIds = allLogEquIds();
        // nowEIds -- 当前设备库所有被借用设备的id
        Set<Integer> nowEIds = new HashSet<>();
        eIds.forEach(eId -> lIds.forEach(lId -> {
            if (lId.equals(eId)) nowEIds.add(lId);
        }));

        // 查询当前设备库被借用设备的 申请记录
        List<EquApprovalLog> logList = new ArrayList<>();
        Set<Integer> appIds = new HashSet<>();

        List<EquApprovalLog> returnList = new ArrayList<>();
        for (Integer nowEId : nowEIds) {
            logList = equApprovalLogService.list(new QueryWrapper<>(new EquApprovalLog(null, nowEId)));
            returnList.addAll(logList);
        }
        return returnList;
    }


    /**
     * 获取当前设备库的所有设备id
     *
     * @param roomId 设备库id
     * @return
     */
    List<Integer> oneRoomEquIds(Integer roomId) {
        // equs -- 当前设备库的所有设备
        List<Equipment> equs = equipmentService.list(new QueryWrapper<>(new Equipment(null, roomId)));
        List<Integer> ids = new ArrayList<>();
        equs.forEach(e -> ids.add(e.getId()));
        return ids;
    }

    /**
     * 获取log记录里面的所有被借用的设备id
     *
     * @return
     */
    List<Integer> allLogEquIds() {
        List<EquApprovalLog> list = equApprovalLogService.list();
        List<Integer> ids = new ArrayList<>();
        list.forEach(log -> {
            if (!log.getApprovalStatusId().equals(8)) ids.add(log.getEquId());
        });
        return ids;
    }
}
