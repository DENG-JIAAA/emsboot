package top.dj.service.impl;

import org.springframework.stereotype.Service;
import top.dj.POJO.DO.UserRole;
import top.dj.mapper.UserRoleMapper;
import top.dj.service.UserRoleService;

/**
 * @author dj
 * @date 2021/2/22
 */
@Service
public class UserRoleServiceImpl extends MyServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
