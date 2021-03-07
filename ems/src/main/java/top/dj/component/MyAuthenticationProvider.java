package top.dj.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.dj.POJO.DO.User;
import top.dj.service.UserService;

/**
 * @author dj
 * @date 2021/2/21
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 前端用户名 和 密码
        String loginUsername = authentication.getName();
        String loginPassword = (String) authentication.getCredentials();
        System.out.println("[provider] login username is: " + loginUsername);
        System.out.println("[provider] login password is: " + loginPassword);
        // 此处的 user 对象，包含了角色信息。
        User user = (User) userService.loadUserByUsername(loginUsername);
        if (user == null) {
            throw new UsernameNotFoundException("MyAuthenticationProvider say --> 用户不存在！");
        }
        String dbPassword = user.getPassword();
        // loginPassword -- 前端输入的用户密码
        // dbPassword -- 数据库存放的用户密码
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 比对用户输入的密码 和 数据库查出的密码是否一致
        if (!passwordEncoder.matches(loginPassword, passwordEncoder.encode(dbPassword))) {
            throw new BadCredentialsException("MyAuthenticationProvider say --> 密码不正确！");
        }

        //return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    /**
     * AuthenticationManager 本身不包含认证逻辑，其核心是用来管理所有的 AuthenticationProvider
     * 通过交由合适的 AuthenticationProvider 来实现认证。
     * ProviderManager 是 AuthenticationManager 的默认实现类
     * 通过下面的 supports 方法来判断是否支持当前方式的认证
     * 这里支持验证 UsernamePasswordAuthenticationToken
     *
     * @param authentication 用户认证
     * @return 是否支持当前方式的认证
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // 使用表单登录则采用此 Provider 进行用户认证
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
