package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.*;
import top.dj.POJO.VO.ApplyInfo;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.EquQueryVO;
import top.dj.POJO.VO.EquVO;
import top.dj.mapper.*;
import top.dj.service.EquipmentService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author dj
 * @date 2021/1/12
 */
@Service
public class EquipmentServiceImpl extends MyServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private EquCateMapper equCateMapper;
    @Autowired
    private EquStateMapper equStateMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private EquApprovalMapper equApprovalMapper;

    /**
     * 封装以适应前端的 EquVO 单个数据
     */
    @Override
    public EquVO findEquVO(Integer id) {
        Equipment equ = new Equipment();
        equ.setId(id);
        equ = equipmentMapper.selectOne(new QueryWrapper<>(equ));
        return convert(equ, new EquVO());
    }

    /**
     * 封装以适应前端的 EquVO 分页数据
     * 对于普通用户来说 应该可以对所有的设备发起借用申请
     * 管理员的话可以查看本设备库的所有设备详细信息
     */
    @Override
    public DataVO<EquVO> findEquVO(Integer page, Integer limit, User nowUser) {
        Integer roomId = userServiceImpl.getRoomIdIfNoSuper(nowUser);
        Wrapper<Equipment> wrapper = new QueryWrapper<>(new Equipment(null, roomId));
        IPage<Equipment> equPage = equipmentMapper.selectPage(new Page<>(page, limit), wrapper);
        return convert(equPage, new DataVO<>());
    }

    @Override
    public DataVO<EquVO> fetchEquListByQuery(User nowUser, EquQueryVO equQueryVO) {
        Integer roomId = userServiceImpl.getRoomIdIfNoSuper(nowUser);
        Integer page = equQueryVO.getPage();
        Integer limit = equQueryVO.getLimit();
        String equName = equQueryVO.getEquName();
        equQueryVO.setEquName("".equals(equName) ? null : equName);

        Equipment equipment = new Equipment();
        copyProperties(equQueryVO, equipment);
        // 说明 非超级管理员 正在请求，设置他的请求范围只在他管理的实践室
        if (roomId != null) {
            equipment.setEquRoom(roomId);
        }
        // 超级管理员请求，看传过来的实践室id进行返回。

        Wrapper<Equipment> wrapper = new QueryWrapper<>(equipment);
        IPage<Equipment> equPage = equipmentMapper.selectPage(new Page<>(page, limit), wrapper);

        return convert(equPage, new DataVO<>());
    }

    @Override
    public Boolean applyForUseEquipment(Integer userId, Integer equId) {
        Equipment equipment = equipmentMapper.selectById(equId);
        equipment.setEquState(7);
        return equipmentMapper.updateById(equipment) == 1;
    }

    /**
     * 用户申请使用设备信息
     *
     * @param applyInfo 申请信息
     * @return
     */
    @Override
    public Boolean applyForUseEquipment(ApplyInfo applyInfo) {
        // 生成一条申请记录
        EquApproval approval = new EquApproval();
        copyProperties(applyInfo, approval);
        approval
                // 设置发出申请的时间（当前时间）
                .setApprovalTime(new Timestamp(System.currentTimeMillis()))
                // 设置申请使用设备的时间（申请人指定）
                .setEquUseTime(new Timestamp(applyInfo.getEquUseTime()))
                // 设置当前申请的状态（默认为：待审核）
                .setApprovalStatusId(1);

        // 设备库存 减 申请使用的设备数量
        Equipment e = equipmentMapper.selectById(applyInfo.getEquId());
        e.setEquQuantity(e.getEquQuantity() - applyInfo.getEquQuantity());
        int update = equipmentMapper.updateById(e);

        // 记录此次申请
        int record = equApprovalMapper.insert(approval);
        return update == 1 && record == 1;
    }

    @Override
    public String uploadFile(HttpServletRequest request) throws IOException {
        return userServiceImpl.upload(request, "file");
    }

    @Override
    public boolean modifyImgUrl(Integer id, String url) {
        // 若上传不成功，直接返回false
        if (url == null) return false;
        Equipment equipment = equipmentMapper.selectById(id);
        // 断言从数据库中查出来的设备不为空
        assert equipment != null;
        equipment.setEquPicture(url);
        return equipmentMapper.updateById(equipment) == 1;
    }

    @Override
    public boolean save(EquVO equVO) {
        Integer userId = userMapper.selectOne(new QueryWrapper<>(new User(equVO.getEquUser()))).getId();
        Integer roomId = roomMapper.selectOne(new QueryWrapper<>(new Room(equVO.getEquRoom()))).getId();

        Equipment equ = new Equipment();
        copyProperties(equVO, equ);
        equ.setEquUser(userId);
        equ.setEquRoom(roomId);

        equ.setEquCate(Integer.parseInt(equVO.getEquCate()));
        equ.setEquState(Integer.parseInt(equVO.getEquState()));

        return equipmentMapper.insert(equ) == 1;
    }

    @Override
    public List<User> getNowRoomUsers(Integer roomId) {
        return userMapper.selectList(new QueryWrapper<>(new User(null, roomId)));
    }


    // 将单个 DO 转换为前端适应的 VO
    public EquVO convert(Equipment equ, EquVO equVO) {
        EquCate equCate = new EquCate();
        EquState equState = new EquState();
        Room room = new Room();
        User user = new User();
        // 将 相同的属性拷贝过来
        copyProperties(equ, equVO);

        // 设置 设备类别
        equCate.setId(equ.getEquCate());
        equCate = equCateMapper.selectOne(new QueryWrapper<>(equCate));
        if (equCate != null) equVO.setEquCate(equCate.getCateName());
        // 设置 设备状态
        equState.setId(equ.getEquState());
        equState = equStateMapper.selectOne(new QueryWrapper<>(equState));
        if (equState != null) equVO.setEquState(equState.getEquState());
        // 设置 设备存放地
        room.setId(equ.getEquRoom());
        room = roomMapper.selectOne(new QueryWrapper<>(room));
        if (room != null) equVO.setEquRoom(room.getRoomName());
        // 设置 设备管理员（登录名）
        user.setId(equ.getEquUser());
        user = userMapper.selectOne(new QueryWrapper<>(user));
        if (user != null) equVO.setEquUser(user.getLoginName());

        return equVO;
    }

    // 将列表DO 转换为前端适应的 列表VO
    public DataVO<EquVO> convert(IPage<Equipment> equPage, DataVO<EquVO> dataVO) {
        List<Equipment> equList = equPage.getRecords();
        List<EquVO> voList = new ArrayList<>();
        EquVO equVO;
        for (Equipment equ : equList) {
            equVO = new EquVO();
            voList.add(convert(equ, equVO));
        }
        dataVO.setData(voList);
        dataVO.setCurrent(equPage.getCurrent());
        dataVO.setSize(equPage.getSize());
        dataVO.setTotal(equPage.getTotal());
        dataVO.setPages(equPage.getPages());
        dataVO.setCode(1);
        dataVO.setMsg("设备分页数据");
        return dataVO;
    }

}
