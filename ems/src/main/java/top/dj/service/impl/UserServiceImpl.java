package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.Role;
import top.dj.POJO.DO.Room;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.UserVO;
import top.dj.mapper.RoleMapper;
import top.dj.mapper.RoomMapper;
import top.dj.mapper.UserMapper;
import top.dj.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author dj
 * @date 2021/1/12
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoomMapper roomMapper;

    public UserServiceImpl() {
        super(User.class);
    }

    /**
     * 通过用户 id 封装以适应前端的 UserVO 单个数据
     */
    @Override
    public UserVO findUserVO(Integer id) {
        User user = new User();
        user.setId(id);
        user = userMapper.selectOne(new QueryWrapper<>(user));
        return convert(user, new UserVO());
    }

    /**
     * 通过用户 登录名 封装以适应前端的 UserVO 单个数据
     */
    @Override
    public UserVO findUserVO(String loginName) {
        User user = new User();
        user.setLoginName(loginName);
        user = userMapper.selectOne(new QueryWrapper<>(user));
        return convert(user, new UserVO());
    }

    /**
     * 封装以适应前端的 UserVO 分页数据
     */
    @Override
    public DataVO<UserVO> findUserVO(Integer page, Integer limit) {
        IPage<User> userPage = userMapper.selectPage(new Page<>(page, limit), null);
        return convert(userPage, new DataVO<>());
    }

    // 将单个 DO 转换为前端适应的 VO
    public UserVO convert(User user, UserVO userVO) {
        Room room = new Room();
        Role role = new Role();
        // 将 相同的属性拷贝过来
        copyProperties(user, userVO);
        // 设置 用户管理的设备库名称
        room.setId(user.getUserRoom());
        room = roomMapper.selectOne(new QueryWrapper<>(room));
        if (room != null) userVO.setUserRoom(room.getRoomName());
        // 设置 用户的角色名称
        role.setId(user.getUserRole());
        role = roleMapper.selectOne(new QueryWrapper<>(role));
        if (role != null) userVO.setUserRole(role.getRoleName());
        return userVO;
    }

    // 将列表DO 转换为前端适应的 列表VO
    public DataVO<UserVO> convert(IPage<User> userPage, DataVO<UserVO> dataVO) {
        List<User> userList = userPage.getRecords();
        List<UserVO> voList = new ArrayList<>();
        UserVO userVO;
        for (User user : userList) {
            userVO = new UserVO();
            voList.add(convert(user, userVO));
        }
        dataVO.setData(voList);
        dataVO.setCurrent(userPage.getCurrent());
        dataVO.setSize(userPage.getSize());
        dataVO.setTotal(userPage.getTotal());
        dataVO.setPages(userPage.getPages());
        dataVO.setCode(1);
        dataVO.setMsg("用户分页数据");
        return dataVO;
    }
}
