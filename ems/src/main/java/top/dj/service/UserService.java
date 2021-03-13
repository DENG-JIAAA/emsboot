package top.dj.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.UserQueryVO;
import top.dj.POJO.VO.UserVO;

public interface UserService extends MyIService<User>, UserDetailsService {
    UserVO findUserVO(Integer id);

    UserVO findUserVO(String loginName);

    DataVO<UserVO> findUserVO(Integer page, Integer limit, User nowUser);

    DataVO<UserVO> fetchUserListByQuery(User nowUser, UserQueryVO userQueryVO);

}
