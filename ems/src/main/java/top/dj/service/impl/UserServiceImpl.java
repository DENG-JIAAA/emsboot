package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import top.dj.POJO.DO.*;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.UserQueryVO;
import top.dj.POJO.VO.UserVO;
import top.dj.component.MyGrantedAuthority;
import top.dj.mapper.RoleMapper;
import top.dj.mapper.RoomMapper;
import top.dj.mapper.UserMapper;
import top.dj.service.PermissionService;
import top.dj.service.RoleService;
import top.dj.service.UserRoleService;
import top.dj.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author dj
 * @date 2021/1/12
 */
@Service
@Slf4j
//@Primary
public class UserServiceImpl extends MyServiceImpl<UserMapper, User> implements UserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private QiNiuService qiNiuService;

    /**
     * 通过用户 id 封装以适应前端的 UserVO 单个数据
     *
     * @param id
     * @return
     */
    @Override

    public UserVO findUserVO(Integer id) {
        User user = new User();
        user.setId(id);
        user = userService.getOne(new QueryWrapper<>(user));
        return convert(user, new UserVO());
    }

    /**
     * 通过用户名获取一个用户
     *
     * @param username
     * @return
     */
    public User loadUser(String username) {
        User user = new User();
        user.setLoginName(username);
        Wrapper<User> wrapper = new QueryWrapper<>(user);
        return userService.getOne(wrapper);
    }

    /**
     * 通过用户 登录名 封装以适应前端的 UserVO 单个数据
     *
     * @param loginName
     * @return
     */
    @Override
    public UserVO findUserVO(String loginName) {
        User user = new User();
        user.setLoginName(loginName);
        user = userService.getOne(new QueryWrapper<>(user));
        return convert(user, new UserVO());
    }

    /**
     * 更新 user 数据
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateById(User user) {
        Integer userId = user.getId();
        // 封装需要更改的用户角色列表
        List<UserRole> userRoleList = new ArrayList<>();
        List<Integer> roleIDList = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        authorities.forEach(authority -> {
            String roleName = authority.getAuthority();
            Integer roleId = roleMapper.selectOne(new QueryWrapper<>(new Role("ROLE_" + roleName))).getId();
            roleIDList.add(roleId);
        });
        for (Integer roleID : roleIDList) {
            // 表示用户只有一个角色，且角色为visitor普通游客，那么需要将管理实践室置为空。（普通用户不管理实践室）
            if (roleIDList.size() == 1 && roleID == 1) {
                user.setUserRoom(0);
                // 如果修改用户失败，返回false
                if (super.getBaseMapper().updateById(user) != 1) return false;
            }
            userRoleList.add(new UserRole(null, userId, roleID));
        }
        // 修改指定用户的角色列表（先把用户角色对应关系全部移除，再添加。）
        boolean remove = removeUserRole(userId);
        boolean save = true;
        // 移除完成后，生成新的用户角色对应关系。
        if (remove) save = userRoleService.saveBatch(userRoleList);
        return remove && save;
    }

    /**
     * 获取用户携带的 角色ID列表
     *
     * @param remark
     * @return
     */
    public List<Integer> getUserRoleIDs(String remark) {
        String string = remark.substring(remark.indexOf("_") + 1);
        String[] strings = string.split(",");
        List<String> roleNameList = new ArrayList<>(Arrays.asList(strings));
        List<Integer> roleIDList = new ArrayList<>();
        if (!roleNameList.isEmpty()) {
            Role role = new Role();
            roleNameList.forEach(roleName -> {
                role.setRoleName("ROLE_" + roleName);
                Role one = roleService.getOne(new QueryWrapper<>(role));
                roleIDList.add(one.getId());
            });
        }
        return roleIDList;
    }


    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean save(User user) {
        // 添加用户
        if (super.getBaseMapper().insert(user) != 1) return false;

        // 获取用户角色ID
        List<Integer> roleIDList = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        authorities.forEach(authority -> {
            String roleName = authority.getAuthority();
            roleIDList.add(roleService.getOne(new QueryWrapper<>(new Role("ROLE_" + roleName))).getId());
        });

        // 添加用户角色列表
        if (roleIDList.isEmpty()) return true;
        List<UserRole> userRoleList = new ArrayList<>();
        roleIDList.forEach(roleID -> userRoleList.add(new UserRole(null, user.getId(), roleID)));
        return userRoleService.saveBatch(userRoleList);
    }

    @Override
    public boolean removeById(Serializable userId) {
        removeUserRole((Integer) userId);
        return super.getBaseMapper().deleteById(userId) == 1;
    }

    /**
     * 清空对应用户的 角色列表
     *
     * @param userId
     * @return
     */
    public boolean removeUserRole(Integer userId) {
        UserRole userRole = new UserRole(null, userId, null);
        Wrapper<UserRole> wrapper = new QueryWrapper<>(userRole);
        boolean empty = userRoleService.list(wrapper).isEmpty();
        if (empty) return true;
        return userRoleService.remove(wrapper);
    }

    /**
     * 封装以适应前端的 UserVO 分页数据
     */
    @Override
    public DataVO<UserVO> findUserVO(Integer page, Integer limit, User nowUser) {
        Integer roomId = getRoomIdIfNoSuper(nowUser);
        Wrapper<User> wrapper = new QueryWrapper<>(new User(null, roomId));
        User user = new User();
        IPage<User> userPage = userService.page(new Page<>(page, limit), wrapper);

        List<User> rawRecords = userPage.getRecords();
        List<User> retRecords = new ArrayList<>();

        for (User record : rawRecords) {
            User one = userService.getOne(new QueryWrapper<>(record));
            retRecords.add(one);
        }
        return convert(userPage.setRecords(retRecords), new DataVO<>());
    }

    @Override
    public DataVO<UserVO> fetchUserListByQuery(User nowUser, UserQueryVO userQueryVO) {
        Integer roomId = getRoomIdIfNoSuper(nowUser);

        Integer page = userQueryVO.getPage();
        Integer limit = userQueryVO.getLimit();
        Integer roleId = userQueryVO.getUserRole();
        Integer room = userQueryVO.getUserRoom();
        String name = userQueryVO.getRealName();
        userQueryVO.setRealName("".equals(name) ? null : name);
        name = userQueryVO.getRealName();

        // 设置 角色条件
        Role setRole = roleService.getById(roleId);
        List<User> roleUsers = new ArrayList<>();
        List<User> queryUsers = new ArrayList<>();

        // 前端没有设置角色
        if (setRole == null) {
            User user = new User();
            copyProperties(userQueryVO, user);
            if (roomId != null) user.setUserRoom(roomId);
            queryUsers = userMapper.selectList(new QueryWrapper<>(user));
        } else {
            // 前端设置了角色
            roleUsers = setRole.getRoleUsers();
            List<User> users;
            if (roomId != null) {
                // 不是超级管理员
                users = new ArrayList<>();
                roleUsers.forEach(user -> {
                    Integer dbRoom = user.getUserRoom();
                    if (dbRoom.equals(roomId)) {
                        users.add(user);
                    }
                });
                roleUsers = users;
            }
            // 查询出当前角色负责设备库的所有满足条件的用户
            for (User roleUser : roleUsers) {
                boolean roomOk = true;
                boolean nameOk = true;
                if (room != null)
                    roomOk = roleUser.getUserRoom().equals(room);
                if (name != null) {
                    nameOk = roleUser.getRealName().equals(name);
                    if (nameOk && roomOk) queryUsers.add(roleUser);
                } else if (roomOk) queryUsers.add(roleUser);
            }
        }

        List<User> userList = new ArrayList<>();
        for (User queryUser : queryUsers) {
            User entity = autoMapper.mapperEntity(queryUser);
            userList.add(entity);
        }

        IPage<User> userPage = new Page<>();
        List<User> onePageUserList = new ArrayList<>();

        int total = userList.size();
        int pages = (int) (Math.ceil(total / limit.floatValue()));
        page = page > pages ? pages : page;
        int pl = page * limit;
        // 查询总数大于等于限制数，需要返回分页结果。
        if (total >= limit) {
            for (int i = pl - limit; i < total && i < pl; i++) {
                onePageUserList.add(userList.get(i));
            }
        }
        userPage
                .setPages(pages)
                .setRecords(onePageUserList.isEmpty() ? userList : onePageUserList)
                .setCurrent(page)
                .setSize(limit)
                .setTotal(userList.size());
        return convert(userPage, new DataVO<>());
    }

    /**
     * 用户修改密码
     *
     * @param request 包含了当前登录用户的信息
     * @param oldPwd  原始密码
     * @param newPwd  新密码
     * @return -1 -- 原始密码错误 | 0 -- 修改失败 | 1 -- 修改成功
     */
    @Override
    public Integer changePwd(HttpServletRequest request, String oldPwd, String newPwd) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        Wrapper<User> wrapper = new QueryWrapper<>(user);
        User one = userMapper.selectById(user.getId());
        if (one != null) {
            if (oldPwd.equals(one.getPassword())) {
                one.setLoginPwd(newPwd);
                return userMapper.updateById(one);
            }
        }
        return -1;
    }

    /**
     * 上传用户头像到七牛云
     * 更新用户在数据库中的头像地址
     *
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public String uploadAndUpdate(HttpServletRequest request) throws IOException {
        // 上传七牛云
        String avatarUrl = upload(request, "avatar");
        // 更新数据库中用户头像地址
        String token = request.getHeader("token");
        User redisUser = redisTemplate.opsForValue().get(token);
        if (redisUser != null) {
            User dbUser = userService.getById(redisUser.getId());
            dbUser.setUserPicture(avatarUrl);
            userMapper.updateById(dbUser);
        }
        return avatarUrl;
    }

    @Override
    public boolean modifyAvatarUrl(Integer id, String url) {
        if (url == null) return false;
        User user = userMapper.selectById(id);
        assert user != null;
        user.setUserPicture(url);
        return userMapper.updateById(user) == 1;
    }

    public String upload(HttpServletRequest request, String name) throws IOException {
        MultipartFile file = ((MultipartRequest) request).getFile(name);

        // 将图片文件转换为字节数组，存储到本地
        /*byte[] bytes = file.getBytes();
        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + originalFilename;
        Path path = Paths.get("D:\\workspace\\idea\\biyesheji\\emsvue_plus\\src\\assets\\images\\avatar\\" + fileName);
        Files.write(path, bytes);*/

        // 将图片存储到七牛云，imgUrl为存储在七牛云的图片地址
        assert file != null;
        return qiNiuService.saveImage(file);
    }

    protected Integer getRoomIdIfNoSuper(User user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        boolean hasSuper = false;
        Integer roomId = user.getUserRoom();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if ("ROLE_SUPERADMIN".equals(authority.getAuthority())) {
                    hasSuper = true;
                    roomId = null;
                    break;
                }
            }
        }
        return roomId;
    }

    /**
     * 将单个 DO 转换为前端适应的 VO
     *
     * @param user
     * @param userVO
     * @return
     */
    public UserVO convert(User user, UserVO userVO) {
        if (user == null) return null;

        Room room = null;
        // 将 相同的属性拷贝过来
        copyProperties(user, userVO);

        // 设置 userVO 管理的设备库名称（用户是管理员需要设置管理的实践室，用户是普通游客不需要设置实践室信息）
        Integer rId = user.getUserRoom();
        if (rId != null) {
            room = new Room(rId);
            room = roomMapper.selectOne(new QueryWrapper<>(room));
        }
        if (room != null) userVO.setUserRoom(room.getRoomName());

        // 设置 userVO 的角色列表
        // 如果 userVO 的角色列表不存在（未设置角色情况下）
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        List<Role> userVORoles = new ArrayList<>();
        Role userRole = new Role();
        for (GrantedAuthority authority : authorities) {
            userRole.setRoleName(authority.toString());
            Wrapper<Role> wrapper = new QueryWrapper<>(userRole);
            Role one = roleMapper.selectOne(wrapper);
            // 设置 userVO 的角色列表
            userVORoles.add(one);
        }
        userVO.setAuthorities(userVORoles);
        return userVO;
    }

    /**
     * 将列表DO 转换为前端适应的 列表VO
     *
     * @param userPage
     * @param dataVO
     * @return
     */
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

    /**
     * 方法主要用于从系统数据中查询并加载具体的用户到Spring Security中
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 使用唯一用户名查询用户信息，此查询为联合查询，查出的用户包含有权限信息。
        User user = loadUser(username);
        // 判断用户是否存在
        if (user != null) {
            Collection<? extends GrantedAuthority> roles = user.getAuthorities();
            // 将用户的角色和权限全部加载到 allAuth 中来
            Collection<GrantedAuthority> allAuth = new HashSet<>();
            for (GrantedAuthority role : roles) {
                allAuth.add(new SimpleGrantedAuthority(role.getAuthority()));
                Role oneRole = roleService.getOne(new QueryWrapper<>(new Role(role.getAuthority())));
                List<Permission> permissions = oneRole.getRolePers();
                for (Permission per : permissions) {
                    if (per != null && per.getAuthority() != null)
                        allAuth.add(new MyGrantedAuthority(per.getPerUrl(), per.getPerMethod()));
                }
            }
            // 返回 UserDetails 实现类
            return new org.springframework.security.core.userdetails
                    .User(user.getUsername(), user.getPassword(), allAuth);
        } else {
            throw new UsernameNotFoundException("loadUserByUsername --> 用户名：" + username + "不存在");
        }
    }
}
