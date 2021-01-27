package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.*;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.EquVO;
import top.dj.mapper.*;
import top.dj.service.EquipmentService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author dj
 * @date 2021/1/12
 */
@Service
public class EquipmentServiceImpl extends BaseServiceImpl<Equipment> implements EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private EquCateMapper equCateMapper;
    @Autowired
    private EquStateMapper equStateMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoomMapper roomMapper;

    public EquipmentServiceImpl() {
        super(Equipment.class);
    }

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
     */
    @Override
    public DataVO<EquVO> findEquVO(Integer page, Integer limit) {
        IPage<Equipment> equPage = equipmentMapper.selectPage(new Page<>(page, limit), null);
        return convert(equPage, new DataVO<>());
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
