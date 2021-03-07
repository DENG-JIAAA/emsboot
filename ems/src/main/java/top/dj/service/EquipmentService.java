package top.dj.service;

import top.dj.POJO.DO.Equipment;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.EquVO;

public interface EquipmentService extends MyIService<Equipment> {

    EquVO findEquVO(Integer id);

    DataVO<EquVO> findEquVO(Integer page, Integer limit);
}
