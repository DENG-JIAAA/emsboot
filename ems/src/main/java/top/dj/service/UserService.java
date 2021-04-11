package top.dj.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.UserQueryVO;
import top.dj.POJO.VO.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UserService extends MyIService<User>, UserDetailsService {
    UserVO findUserVO(Integer id);

    UserVO findUserVO(String loginName);

    DataVO<UserVO> findUserVO(Integer page, Integer limit, User nowUser);

    DataVO<UserVO> fetchUserListByQuery(User nowUser, UserQueryVO userQueryVO);

    // 用户修改密码
    Integer changePwd(HttpServletRequest request, String oldPwd, String newPwd);

    // 上传用户头像
    String uploadAndUpdate(HttpServletRequest request) throws IOException;

}
