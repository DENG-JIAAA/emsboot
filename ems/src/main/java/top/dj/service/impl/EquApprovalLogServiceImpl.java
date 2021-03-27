package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.DO.EquApprovalLog;
import top.dj.POJO.DO.Equipment;
import top.dj.mapper.EquApprovalLogMapper;
import top.dj.service.EquApprovalLogService;
import top.dj.service.EquipmentService;
import top.dj.service.UserService;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean recordLog(EquApproval equApproval) {
        EquApprovalLog equApprovalLog = new EquApprovalLog();
        BeanUtils.copyProperties(equApproval, equApprovalLog);
        equApprovalLog.setId(null);
        equApprovalLog.setOriginId(equApproval.getId());
        return equApprovalLogService.save(equApprovalLog);
    }

    @Override
    public Integer numOfBorrow(Integer userId) {
        List<Integer> eIds = oneRoomEquIds(userService.getById(userId).getUserRoom());
        List<Integer> lIds = allLogEquIds();
        List<Integer> ids = new ArrayList<>();
        eIds.forEach(eId -> lIds.forEach(lId -> {
            if (lId.equals(eId)) ids.add(lId);
        }));
        return ids.size();
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
