package top.dj.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import top.dj.POJO.DO.Permission;
import top.dj.mapper.PermissionMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 【获取】这个 url 具备的全部角色权限，返回给决策类使用。
 *
 * @author dj
 * @date 2021/3/2
 */
@Component
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionMapper permissionMapper;

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<Permission> permissions = permissionMapper.selectList(null);
        for (Permission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getPerName());
            // 此处只添加了权限的名字，其实还可以添加更多权限的信息，
            // 例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            // 用权限的getPerUrl() 作为map的key，用 ConfigAttribute 的集合作为 value，
            map.put(permission.getPerUrl(), array);
        }

    }

    /**
     * 此方法是为了判定用户请求的 url 是否在权限表中，
     * 如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     * 不想每一次来了请求，都先要匹配一下权限表中的信息是不是包含此 url。
     * 希望在 MyAccessDecisionManager 的 decide 方法中做拦截还是放行的决策。
     *
     * @param object 包含了用户发起请求的一些信息
     * @return
     * @throws IllegalArgumentException
     */
    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    //因为我不想每一次来了请求，都先要匹配一下权限表中的信息是不是包含此url，
    //我准备直接拦截，不管请求的url 是什么都直接拦截，然后在MyAccessDecisionManager的decide 方法中做拦截还是放行的决策。
    //所以此方法的返回值不能返回 null 此处我就随便返回一下。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        /*if (map == null) loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (String s : map.keySet()) {
            resUrl = s;
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;*/

        Collection<ConfigAttribute> collection = new ArrayList<>();
        collection.add(new SecurityConfig("null"));
        return collection;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
