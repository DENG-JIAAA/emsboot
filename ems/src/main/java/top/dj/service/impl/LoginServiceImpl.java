package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.User;
import top.dj.mapper.UserMapper;
import top.dj.service.LoginService;

/**
 * @author dj
 * @date 2021/1/26
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

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
}
