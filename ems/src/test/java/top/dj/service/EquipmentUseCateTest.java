package top.dj.service;

import com.github.dreamyoung.mprelation.AutoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.EquUseCate;
import top.dj.POJO.DO.Equipment;
import top.dj.mapper.EquUseCateMapper;
import top.dj.mapper.EquipmentMapper;

/**
 * @author dj
 * @date 2021/3/9
 */

@SpringBootTest
public class EquipmentUseCateTest {
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquUseCateMapper equUseCateMapper;
    @Autowired
    private AutoMapper autoMapper;

    @Test
    void test01() {
//        System.out.println("equipmentMapper.selectById(1) = " + equipmentMapper.selectById(1));
//        System.out.println("equUseCateMapper.selectById(1) = " + equUseCateMapper.selectById(1));

        Equipment equipment = new Equipment(1);
        EquUseCate equUseCate = new EquUseCate(1);
        Equipment mapperEntity1 = autoMapper.mapperEntity(equipment);
        EquUseCate mapperEntity2 = autoMapper.mapperEntity(equUseCate);

        System.out.println("mapperEntity1 = " + mapperEntity1);
        System.out.println("mapperEntity2 = " + mapperEntity2);

    }
}
