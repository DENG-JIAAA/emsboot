package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.User;
import top.dj.POJO.DO.UserRole;
import top.dj.mapper.UserMapper;
import top.dj.mapper.UserRoleMapper;
import top.dj.service.LoginService;
import top.dj.service.UserService;

import java.util.List;

/**
 * @author dj
 * @date 2021/1/26
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("pwdEncoder")
    private PasswordEncoder pwdEncoder;
    @Autowired
    private UserRoleMapper userRoleMapper;

    public User getOneByNameAndPwd(User user) {
        Wrapper<User> userWrapper = new QueryWrapper<>(user);
        return userMapper.selectOne(userWrapper);
    }

    @Override
    public Boolean isThisUserExist(User user) {
        return getUserByLoginInfo(user) != null;
    }

    @Override
    public User getUserByLoginInfo(User user) {
        return userMapper.selectOne(new QueryWrapper<>(user));
    }

    @Override
    public Boolean register(User user) {
        List<User> userList = userMapper.selectList(null);
        for (User one : userList) {
            if (user.getLoginName().equals(one.getLoginName())) return false;
        }
        // 记得注册的时候把密码加密一下
        // user.setLoginPwd(pwdEncoder.encode(user.getLoginPwd()));

        // 添加用户成功就在用户数据库中查询用户的 主键id
        int insert = userMapper.insert(user);
        Integer uId = null;
        if (insert == 1) {
            uId = userMapper.selectOne(new QueryWrapper<>(new User(user.getLoginName()))).getId();
            // 设置所有新注册的用户为普通游客
            return userRoleMapper.insert(new UserRole(uId, 1)) == 1;
        }
        return false;
    }
}
