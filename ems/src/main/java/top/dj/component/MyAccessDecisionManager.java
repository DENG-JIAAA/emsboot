package top.dj.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 授权管理器（【决策】是否返回用户请求的资源）
 *
 * @author dj
 * @date 2021/3/2
 */
@Component
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 判定是否拥有权限的决策方法
     *
     * @param authentication   用户的认证信息
     * @param object           包含了客户端发起请求的 request 信息。
     *                         可进行转换：HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param configAttributes 为 MyInvocationSecurityMetadataSource 的 getAttributes(Object object)这个方法返回的结果，
     *                         此方法是为了判定用户请求的 url 是否在权限表中，
     *                         如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。
     *                         如果不在权限表中则放行。
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    // https://blog.csdn.net/u012373815/article/details/55225079
    // https://codechina.csdn.net/mirrors/527515025/springboot?utm_source=csdn_github_accelerator
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        String url, method;
        AntPathRequestMatcher matcher;

        // 不用每一次来了请求，都先要匹配一下权限表中的信息是不是包含此url，
        // 可以直接拦截，不管请求的url 是什么都直接拦截，
        // 然后在 MyAccessDecisionManager 的 decide 方法中做拦截还是放行的决策。
        // if (null == configAttributes) return;

        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        for (GrantedAuthority ga : grantedAuthorities) {
            if (ga.getAuthority().equals("ROLE_SUPERADMIN")) {
                // 超级管理员拥有所有权限，直接放行。
                return;
            } else if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {
                // 未登录只允许访问 login和register 页面
                matcher = new AntPathRequestMatcher("/login");
                if (matcher.matches(request))
                    return;
                matcher = new AntPathRequestMatcher("/register");
                if (matcher.matches(request))
                    return;
            } else if (ga instanceof MyGrantedAuthority) {
                MyGrantedAuthority perGrantedAuthority = (MyGrantedAuthority) ga;
                url = perGrantedAuthority.getUrl();
                method = perGrantedAuthority.getMethod();
                matcher = new AntPathRequestMatcher(url);
                // 用户的分配的 权限url 和 请求url 进行比对
                if (matcher.matches(request)) {
                    // 当权限表权限的 method 为 ALL 时表示拥有此路径的所有请求方式权利。
                    String requestMethod = request.getMethod();
                    if (method.equals(requestMethod) || "ALL".equals(method)) {
                        log.info("用户拥有相应权限，响应资源。");
                        return;
                    }
                }
            }
        }
        throw new AccessDeniedException("用户没有相应权限，抛出异常。");

        /*ConfigAttribute c;
        String needAuthority;
        for (ConfigAttribute configAttribute : configAttributes) {
            c = configAttribute;
            needAuthority = c.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (needAuthority.trim().equals(authority.getAuthority())) {
                    log.info("用户拥有相应权限，直接放行。");
                    return;
                }
            }
        }
        throw new AccessDeniedException("用户没有相应权限，抛出异常。");*/
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
