package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.EquApprovalLog;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.User;
import top.dj.mapper.EquApprovalLogMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dj
 * @date 2021/3/18
 */
@SpringBootTest
public class EquApprovalLogServiceTest {
    @Autowired
    private EquApprovalLogService equApprovalLogService;
    @Autowired
    private EquApprovalLogMapper equApprovalLogMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private EquipmentService equipmentService;


    @Test
    void test01() {
        // 当前用户id -- 103
        User user = userService.getById(103);
        Integer roomId = user.getUserRoom();

        // 获取当前设备库中的所有设备
        Equipment equ = new Equipment(null, roomId);
        List<Equipment> equs = equipmentService.list(new QueryWrapper<>(equ));
        List<Equipment> list = new ArrayList<>();

        for (Equipment e : equs) {
            list.add(equipmentService.getOne(new QueryWrapper<>(e)));
        }


        // list -- 当前设备库所有的设备
        for (Equipment one : list) {

        }

    }

    @Test
    void getPublic() {
        List<Integer> allEquId = getAllEquId(3);
        List<Integer> logEquId = getAllLogEquId();

//        System.out.println("allEquId = " + allEquId);
//        System.out.println("logEquId = " + logEquId);

        List<Integer> ids = new ArrayList<>();
        for (Integer eId : allEquId) {
            for (Integer logEId : logEquId) {
                if (logEId.equals(eId)) {
                    ids.add(logEId);
                }
            }
        }

        System.out.println("ids = " + ids);

    }

    @Test
    List<Integer> getAllEquId(Integer roomId) {
        // 获取当前设备库中的所有设备
        Equipment equ = new Equipment(null, roomId);
        List<Equipment> equs = equipmentService.list(new QueryWrapper<>(equ));
        List<Integer> integers = new ArrayList<>();
        for (Equipment e : equs) {
            integers.add(e.getId());
        }
        return integers;
    }

    @Test
    List<Integer> getAllLogEquId() {
        List<EquApprovalLog> list = equApprovalLogService.list();
        List<Integer> integers = new ArrayList<>();
        for (EquApprovalLog log : list) {
            if (!log.getApprovalStatusId().equals(8)) {
                integers.add(log.getEquId());
            }
        }
        return integers;
    }
}
