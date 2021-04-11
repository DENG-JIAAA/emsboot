package top.dj.service;

import top.dj.POJO.DO.User;

/**
 * @author dj
 * @date 2021/1/26
 */
public interface LoginService {
    public Boolean isThisUserExist(User user);

    public User getUserByLoginInfo(User user);

    public Boolean register(User user);

}
