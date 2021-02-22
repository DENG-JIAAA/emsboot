package top.dj.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.UserForAuth;
import top.dj.mapper.UserForAuthMapper;
import top.dj.service.UserForAuthService;

/**
 * @author dj
 * @date 2021/2/14
 */
@Service
@Slf4j
public class UserForAuthServiceImpl extends ServiceImpl<UserForAuthMapper, UserForAuth> implements UserForAuthService, UserDetailsService {
    @Autowired
    private UserForAuthMapper userForAuthMapper;
    @Autowired
    private UserForAuthService userForAuthService;


    @Override
    // 当有用户登录时，spring security会调用loadUserByUsername方法，并把用户输入的账号传进来
    // 1、前端发送的请求数据格式为 json 格式的数据
    // 2、SpringSecurity 的配置需要提交的数据形式是 表单格式的
    // https://blog.csdn.net/qq_43751336/article/details/110941435
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("执行了自定义登录逻辑，登录名为：{}。", username);
        UserForAuth user = new UserForAuth();
        user.setLoginName(username);
        Wrapper<UserForAuth> wrapper = new QueryWrapper<>(user);
        // UserForAuth one = userForAuthMapper.selectOne(wrapper);

        UserForAuth one = userForAuthService.getOne(wrapper);

        if (one == null) {
            throw new UsernameNotFoundException("用户名未找到");
        }
//        List<GrantedAuthority> authorityList =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("add,query,ROLE_admin,ROLE_manager");

        /*List<RoleForAuth> userRoles = one.getUserRoles();
        List<String> roleNames = new ArrayList<>();
        for (RoleForAuth userRole : userRoles) {
            String roleName = userRole.getRoleName();
            roleNames.add(roleName);
        }
        System.out.println("userRoles = " + userRoles);
        System.out.println("roleNames = " + roleNames);*/
        return one;
        // return new User((one.getUsername()), new BCryptPasswordEncoder().encode(one.getPassword()), authorityList);
    }
}
