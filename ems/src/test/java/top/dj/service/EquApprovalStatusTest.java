package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.AutoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.User;
import top.dj.mapper.EquApprovalMapper;
import top.dj.mapper.EquApprovalStatusMapper;
import top.dj.mapper.EquipmentMapper;
import top.dj.service.impl.EquApprovalServiceImpl;
import top.dj.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dj
 * @date 2021/3/10
 */
@SpringBootTest
public class EquApprovalStatusTest {
    @Autowired
    private AutoMapper autoMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private EquApprovalStatusMapper equApprovalStatusMapper;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquApprovalMapper equApprovalMapper;
    @Autowired
    private EquCateService equCateService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EquApprovalServiceImpl equApprovalServiceImpl;

    @Test
    void test01() {
        Equipment equipment = new Equipment(6);

        System.out.println("autoMapper.mapperEntity(1) = " + autoMapper.mapperEntity(equipment));
        System.out.println("equipmentMapper.selectById(1) = " + equipmentMapper.selectById(6));
        System.out.println("equApprovalStatusMapper.selectById(1) = " + equApprovalStatusMapper.selectById(1));
        System.out.println("equipmentService.getById(1) = " + equipmentService.getById(6));
    }

    /*@Test
    void test02() {
        ApplyInfo info = new ApplyInfo(1, 10, 1000, 4, System.currentTimeMillis(), "申请使用设备的备注信息");
        EquApproval approval = new EquApproval();
        copyProperties(info, approval);

        System.out.println("approval = " + approval);
    }*/

    @Test
    void test03() {
        EquApproval approval = new EquApproval(null, 101);
        Wrapper<EquApproval> wrapper = new QueryWrapper<>(approval);

        List<EquApproval> approvals = equApprovalMapper.selectList(wrapper);

        for (EquApproval a : approvals) {
            System.out.println("a = " + a);
        }

    }

    @Test
    void test04() {
        // 知道当前用户的id：103
        // 要查出所有用户管理的设备 【被申请的】

        // 1、管理员103管理的设备（3、6、25、136、186）
        Equipment equipment = new Equipment();
        equipment.setEquUser(103);
        Wrapper<Equipment> wrapper = new QueryWrapper<>(equipment);
        List<Equipment> equipmentList = equipmentMapper.selectList(wrapper);

        /*for (Equipment equ : equipmentList) {
            System.out.println("用户103管理的设备equ = " + equ);
        }*/

        //2、查看被申请的设备（3、6）

        // 所有被申请的设备
        EquApproval approval = new EquApproval();
        List<EquApproval> approvalList = equApprovalMapper.selectList(null);
        // approvalList.forEach(System.out::println);


        List<EquApproval> list = new ArrayList<>();
        // 所有被申请的设备
        for (EquApproval a : approvalList) {
            // 用户管理的设备
            for (Equipment e : equipmentList) {
                if (e.getId().equals(a.getEquId())) {
                    // 用户管理的设备有被申请的，就添加进list
                    list.add(a);
                }
            }
        }
        list.forEach(System.out::println);
    }

    @Test
    void test05() {
        Equipment e = new Equipment();
        e.setEquCate(4);

        List<Equipment> equipmentList = equipmentMapper.selectList(new QueryWrapper<>(e));
        for (Equipment equipment : equipmentList) {
            System.out.println("equipment = " + equipment);
        }
    }

    @Test
    void test06() {


        User visitor = userService.getOne(new QueryWrapper<>(new User("visitor6")));
        User admin6 = userService.getOne(new QueryWrapper<>(new User("admin6")));
        User sadmin6 = userService.getOne(new QueryWrapper<>(new User("sadmin6")));
        User djosimon = userService.getOne(new QueryWrapper<>(new User("djosimon")));

        boolean b = equApprovalServiceImpl.AdminOrSuper(visitor);
        boolean b1 = equApprovalServiceImpl.AdminOrSuper(admin6);
        boolean b2 = equApprovalServiceImpl.AdminOrSuper(sadmin6);
        boolean b3 = equApprovalServiceImpl.AdminOrSuper(djosimon);

        System.out.println();
    }

}
