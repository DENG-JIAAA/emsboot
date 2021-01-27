package top.dj.service.impl;

import org.springframework.stereotype.Service;
import top.dj.POJO.DO.Role;
import top.dj.service.RoleService;

/**
 * @author dj
 * @date 2021/1/13
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    public RoleServiceImpl() {
        super(Role.class);
    }
}
