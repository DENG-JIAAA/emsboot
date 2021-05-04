package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.AutoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.Room;
import top.dj.POJO.DO.User;
import top.dj.POJO.DO.UserRole;
import top.dj.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author dj
 * @date 2021/3/7
 */

@SpringBootTest
public class EqupmentServiceTest {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AutoMapper autoMapper;

    @Test
    void test01() {
        Equipment equipment = new Equipment();
        equipment.setEquName("");

        Equipment one = equipmentService.getOne(new QueryWrapper<>(equipment));

        System.out.println("one = " + one);
    }

    @Test
    void test02() {
        List<Equipment> allE = equipmentService.list(null);
        ArrayList<Equipment> equipmentArrayList = new ArrayList<>();

        for (Equipment e : allE) {
            Equipment one = equipmentService.getOne(new QueryWrapper<>(e));
            equipmentArrayList.add(one);
//            Integer ri = e.getEquRoom();
//            // 在这个room里面随便找一个user来设置
//            Room room = roomService.getById(ri);
        }

        for (Equipment equipment : equipmentArrayList) {
            Integer rid = equipment.getEquRoom();

            User user = new User();
            user.setUserRoom(rid);
            List<User> userList = userService.list(new QueryWrapper<>(user));
            ArrayList<User> users = new ArrayList<>();

            for (User one : userList) {
                users.add(userService.getOne(new QueryWrapper<>(one)));
            }

            ArrayList<Integer> integers = new ArrayList<>();
            for (User u : users) {
                integers.add(u.getId());
            }

            Random random = new Random();
            int n = random.nextInt(integers.size());
            Integer integer = integers.get(n);
            equipment.setEquUser(integer);
            equipmentService.update(new QueryWrapper<>(equipment));
        }
    }

    @Test
    void test02_1_before() {
        List<Equipment> equipmentList = equipmentService.list();
        List<Equipment> equs = new ArrayList<>();
        for (Equipment e : equipmentList) {
            equs.add(equipmentService.getOne(new QueryWrapper<>(e)));
        }
        for (Equipment equ : equs) {
            test02_1_after(equ);
        }
    }

    @Test
    void test02_1_after(Equipment equipment) {
        Integer roomId = equipment.getEquRoom();

        List<User> userList = userService.list(new QueryWrapper<>(new User(null, roomId)));
        List<User> users = new ArrayList<>();
        for (User user : userList) {
            users.add(userService.getOne(new QueryWrapper<>(user)));
        }

        List<Integer> integers = new ArrayList<>();
        for (User user : users) {
            integers.add(user.getId());
        }

        Random random = new Random();
        int index = random.nextInt(integers.size());
        Integer uId = integers.get(index);
        equipment.setEquUser(uId);
        equipmentService.updateById(equipment);
    }

    @Test
    void test03() {
        Room room = roomService.getById(1);
        User user = new User();
        user.setUserRoom(1);
        List<User> userList = userService.list(new QueryWrapper<>(user));
        ArrayList<User> users = new ArrayList<>();

        for (User one : userList) {
            users.add(userService.getOne(new QueryWrapper<>(one)));
        }

        for (User u : users) {
            System.out.println("u = " + u);
        }

    }

    @Test
    void test04() {
        List<User> list = userService.list(null);
        for (User user : list) {
            if (user.getAuthorities().isEmpty()) {
                userRoleService.save(new UserRole(user.getId(), 1));
            }
        }

    }

    @Test
    void test04_1() {
        UserRole ur = new UserRole(30, 1);
        userRoleService.save(ur);
    }

    @Test
    void test04_2() {
        List<User> list = userService.list(new QueryWrapper<>(null));
        ArrayList<User> users = new ArrayList<>();
        for (User user : list) {
            User one = userService.getOne(new QueryWrapper<>(user));
            users.add(one);
        }

        for (User user : users) {
            if (user.getAuthorities().isEmpty()) {
                userRoleService.save(new UserRole(user.getId(), 1));
            }
        }

    }

    @Test
    void test05() {
        User user = userService.getById(30);
        User admin = userService.getById(1);

//        System.out.println("user.getAuthorities() = " + user.getAuthorities());
//        System.out.println("admin.getAuthorities() = " + admin.getAuthorities());

        System.out.println("user.getAuthorities().isEmpty() = " + user.getAuthorities().isEmpty());
    }

    @Test
    void test06() {
        // 查看一个管理员负责的所有设备
        Integer uId = 102;

        User user = userService.getById(uId);
        Integer roomId = user.getUserRoom();

        // 查看当前设备库的所有设备
        Equipment e = new Equipment(null, roomId);
        List<Equipment> equipmentList = equipmentService.list(new QueryWrapper<>(e));


        ArrayList<Equipment> list = new ArrayList<>();
        for (Equipment equ : equipmentList) {
            list.add(equipmentService.getOne(new QueryWrapper<>(equ)));
        }

        ArrayList<Equipment> es = new ArrayList<>();
        for (Equipment equ : list) {
            if (equ.getEquUser().equals(uId)) {
                es.add(equ);
            }
        }

        for (Equipment equipment : es) {

            System.out.println("equipment = " + equipment);
        }
    }

    @Test
    void test07() {
        Equipment equipment = equipmentService.getById(2);
        System.out.println("equipment = " + equipment);
//        equipment.setOutFirmTime(new Timestamp(0));
//        equipment.setPurchaseTime(new Timestamp(0));
        equipment.setOutFirmTime(null);
        equipment.setPurchaseTime(null);
        boolean update = equipmentService.updateById(equipment);
        System.out.println("update = " + update);
    }

}
