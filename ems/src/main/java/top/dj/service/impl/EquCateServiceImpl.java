package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.EquCate;
import top.dj.POJO.DO.Equipment;
import top.dj.mapper.EquCateMapper;
import top.dj.mapper.EquipmentMapper;
import top.dj.service.EquCateService;

import java.util.*;

/**
 * @author dj
 * @date 2021/1/20
 */
@Service
public class EquCateServiceImpl extends MyServiceImpl<EquCateMapper, EquCate> implements EquCateService {
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private EquCateMapper equCateMapper;

    @Override
    public Map<Integer, Integer> getTheMapOfCate() {
        Equipment e = new Equipment();
        Map<Integer, Integer> map = new HashMap<>();
        Integer count = equCateMapper.selectCount(null);
        for (int i = 1; i <= count; i++) {
            e.setEquCate(i);
            List<Equipment> list = equipmentMapper.selectList(new QueryWrapper<>(e));
            String cateName = equCateMapper.selectById(i).getCateName();
            map.put(i, list.size());
        }
        return map;
    }

    @Override
    public Map<Integer, String> getTheMapWithNameOfCate() {
        Equipment e = new Equipment();
        Map<Integer, String> cateMap = new HashMap<>();

        List<EquCate> equCates = equCateMapper.selectList(null);
        int key = 0;
        for (EquCate equCate : equCates) {
            Integer id = equCate.getId();
            String name = equCate.getCateName();
            e.setEquCate(id);
            // 找出所有类别为id的设备 -- eList
            List<Equipment> eList = equipmentMapper.selectList(new QueryWrapper<>(e));
            // 返回设备的map形式: key -- "key", value -- "id-name-quantity"
            cateMap.put(key, id + "-" + name + "-" + eList.size());
            key++;
        }
        return cateMap;
    }
}
