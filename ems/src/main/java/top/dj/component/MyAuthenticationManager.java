package top.dj.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import top.dj.service.UserForAuthService;

/**
 * @author dj
 * @date 2021/2/22
 */
//@Component
public class MyAuthenticationManager implements AuthenticationManager {
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    private UserForAuthService userForAuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
