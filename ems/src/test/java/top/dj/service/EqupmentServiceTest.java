package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Equipment;

/**
 * @author dj
 * @date 2021/3/7
 */

@SpringBootTest
public class EqupmentServiceTest {
    @Autowired
    private EquipmentService equipmentService;

    @Test
    void test01() {
        Equipment equipment = new Equipment();
        equipment.setEquName("");

        Equipment one = equipmentService.getOne(new QueryWrapper<>(equipment));

        System.out.println("one = " + one);
    }
}
