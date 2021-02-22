package top.dj.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import top.dj.POJO.DO.UserForAuth;
import top.dj.service.UserForAuthService;

import java.util.Collection;

/**
 * @author dj
 * @date 2021/2/21
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserForAuthService userForAuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("name is : " + name);
        System.out.println("password is : " + password);
        UserForAuth multiOne = (UserForAuth) userForAuthService.loadUserByUsername(name);

        if (multiOne == null) {
            throw new BadCredentialsException("用户名不存在");
        }
        if (!multiOne.getLoginPwd().equals(password)) {
            throw new BadCredentialsException("密码错误");
        }

        Collection<? extends GrantedAuthority> authorities = multiOne.getAuthorities();
        return new UsernamePasswordAuthenticationToken(multiOne, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // return true;
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
