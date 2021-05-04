package top.dj.service;

import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.ApplyInfo;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.EquQueryVO;
import top.dj.POJO.VO.EquVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface EquipmentService extends MyIService<Equipment> {

    EquVO findEquVO(Integer id);

    DataVO<EquVO> findEquVO(Integer page, Integer limit, User nowUser);

    DataVO<EquVO> fetchEquListByQuery(User nowUser, EquQueryVO equQueryVO);

    Boolean applyForUseEquipment(Integer userId, Integer equId);

    Boolean applyForUseEquipment(ApplyInfo applyInfo);

    String uploadFile(HttpServletRequest request) throws IOException;

    boolean modifyImgUrl(Integer id, String url);

    boolean save(EquVO equVO);

    List<User> getNowRoomUsers(Integer roomId);
}
