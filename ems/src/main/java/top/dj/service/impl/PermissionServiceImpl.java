package top.dj.service.impl;

import org.springframework.stereotype.Service;
import top.dj.POJO.DO.Permission;
import top.dj.mapper.PermissionMapper;
import top.dj.service.PermissionService;

/**
 * @author dj
 * @date 2021/1/13
 */
@Service
public class PermissionServiceImpl extends MyServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
