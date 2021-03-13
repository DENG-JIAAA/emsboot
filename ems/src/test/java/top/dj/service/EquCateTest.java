package top.dj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Equipment;
import top.dj.mapper.EquCateMapper;
import top.dj.mapper.EquipmentMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dj
 * @date 2021/3/11
 */
@SpringBootTest
public class EquCateTest {
    @Autowired
    private EquCateService equCateService;
    @Autowired
    private EquCateMapper equCateMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Test
    void test01() {
        Equipment e = new Equipment();

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i < 9; i++) {
            e.setEquCate(i);
            List<Equipment> list = equipmentMapper.selectList(new QueryWrapper<>(e));
            String cateName = equCateMapper.selectById(i).getCateName();
            int size = list.size();
            map.put(i, size);
        }
    }
}
