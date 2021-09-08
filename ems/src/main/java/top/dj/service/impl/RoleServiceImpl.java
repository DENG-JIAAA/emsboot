package top.dj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.Menu;
import top.dj.POJO.DO.Permission;
import top.dj.POJO.DO.Role;
import top.dj.POJO.DO.UserRole;
import top.dj.POJO.VO.RoleVO;
import top.dj.mapper.*;
import top.dj.service.PermissionService;
import top.dj.service.RoleService;

import java.util.*;

/**
 * @author dj
 * @date 2021/1/13
 */
@Service
public class RoleServiceImpl extends MyServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MetaMapper metaMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public List<RoleVO> getRoleVOS() {
        List<Role> roles = roleMapper.selectList(null);
        List<RoleVO> roleVOS = new ArrayList<>();
        RoleVO roleVO;
        String roleName;
        if (roles != null) {
            // 转换成适应前端的VO
            for (Role role : roles) {
                roleVO = new RoleVO();
                roleName = role.getRoleName().substring(5);
                roleVO.setKey(roleName.toLowerCase());
                roleVO.setName(roleName);
                roleVO.setDescription(role.getRoleDescription());
                roleVOS.add(roleVO);
            }
        }
        return roleVOS;
    }

    @Override
    public Map<Integer, String> getTheMapOfRole() {
        Map<String, List<Integer>> map = getRoleUserIds();
        int visitorSize = map.get("VISITOR").size();
        int adminSize = map.get("ADMIN").size();
        int superAdminSize = map.get("SUPERADMIN").size();

        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(0, 1 + "-" + "游客" + "-" + visitorSize);
        roleMap.put(1, 2 + "-" + "管理员" + "-" + adminSize);
        roleMap.put(2, 3 + "-" + "超级管理员" + "-" + superAdminSize);
        return roleMap;
    }

    /**
     * 获取指定 角色字符串的 用户id集合
     *
     * @return
     */
    public Map<String, List<Integer>> getRoleUserIds() {
        List<UserRole> userRoles = userRoleMapper.selectList(null);
        List<Integer> visitorIds = new ArrayList<>();
        List<Integer> adminIds = new ArrayList<>();
        List<Integer> superAdminIds = new ArrayList<>();
        Map<String, List<Integer>> roleMap = new HashMap<>();

        userRoles.forEach(userRole -> {
            Integer roleId = userRole.getRoleId();
            Integer userId = userRole.getUserId();
            if (roleId.equals(1)) visitorIds.add(userId);
            if (roleId.equals(2)) adminIds.add(userId);
            if (roleId.equals(3)) superAdminIds.add(userId);
        });

        roleMap.put("VISITOR", visitorIds);
        roleMap.put("ADMIN", adminIds);
        roleMap.put("SUPERADMIN", superAdminIds);
        return roleMap;
    }

//    @Override
//    public RoleVO getOneRoleVO(Integer roleId) {
//        Role rawRole = roleService.getById(roleId);
//        RoleVO roleVO = new RoleVO();
//
//        if (rawRole != null) {
//            // 数据库存在这个角色，开始设置vo的信息
//            String roleName = rawRole.getRoleName().substring(5);
//            roleVO.setKey(roleName.toLowerCase());
//            roleVO.setName(roleName);
//            roleVO.setDescription(rawRole.getRoleDescription());
//
//            List<Permission> permissionList = rawRole.getRolePers();
//            List<Menu> menus = new ArrayList<>();
//
//            if (permissionList != null && !permissionList.isEmpty()) {
//                // 用户有权限，才进行 permission 到 menu 的转换（处理组件的嵌套）
//
//                Set<Integer> parentIdSet = getParentIdSet(permissionList);
//                if (!parentIdSet.isEmpty()) {
//                    // 用户的权限有父、子组件嵌套关系才获取【每一个父组件的所有子组件】
//                    List<List<Permission>> allFathersChildren =
//                            getAllFathersChildren(permissionList, parentIdSet);
//                    for (List<Permission> oneFathersChildren : allFathersChildren) {
//                        for (Permission child : oneFathersChildren) {
//
//                        }
//                    }
//
//                }
//                // 用户权限没有父、子组件的嵌套关系（全都是单组件）
//
//
//                List<Permission> subPerList = new ArrayList<>();
//                List<Menu> subMenuList = new ArrayList<>();
//                if (!parentIdSet.isEmpty()) {
//                    // 代表这个角色的权限中有父、子组件嵌套的情况（调用generate方法后添加到menus）
//                    for (Integer parentId : parentIdSet) {
//                        for (Permission permission : permissionList) {
//                            Integer id = permission.getId();
//                            Permission one = permissionService.getOne(new QueryWrapper<>(new Permission(id, parentId)));
//                            // 当前用户有权限的permission（且它的父节点为parentId），放在list中，待转换。
//                            Menu menu;
//                            if (one != null) {
//                                menu = new Menu();
//                                copyProperties(one, menu);
//                                subMenuList.add(menu);
//                            }
//                        }
//                    }
//
//                    generateMenu(subMenuList, );
//                }
//                // 没有父组件的情况，直接添加进menus即可
//
//                // TODO for循环中
//                //  1、父、子组件嵌套的情况（调用generate）
//                //  2、没有父组件的情况 也要添加进menus
//                if (!parentIdSet.isEmpty()) {
//                    for (Integer pId : parentIdSet) {
//                        Permission per = new Permission(null, pId);
//
//                        Permission pPer = permissionService.getById(pId);
//                        List<Permission> subPers = permissionService.list(new QueryWrapper<>(per));
//                        List<Menu> subMenus = new ArrayList<>();
//                        Menu subMenu = null;
//
//                        for (Permission subPer : subPers) {
//                            subMenu = new Menu();
//                            copyProperties(subPer, subMenu);
//                            subMenu.setMeta(metaMapper.selectById(subPer.getMeta()));
//                            subMenus.add(subMenu);
//                        }
//
//                        Menu pMenu = new Menu();
//                        pMenu.setMeta(metaMapper.selectById(pPer.getMeta()));
//                        copyProperties(pPer, pMenu);
//                        // oneMenu包含了它本身 和 它的子组件们
//                        Menu oneMenu = generateMenu(subMenus, pMenu);
//                        menus.add(oneMenu);
//                    }
//                }
//            }
//
//            roleVO.setRoutes(menus);
//        }
//        return roleVO;
//    }

    private List<List<Permission>> getAllFathersChildren(List<Permission> oneRolePerList, Set<Integer> parentIdSet) {

        return null;
    }

    private Set<Integer> getParentIdSet(List<Permission> permissionList) {
        // 获取有父、子组件嵌套关系的父组件id

        Set<Integer> parentIdSet = new HashSet<>();
        // 分组找出所有有嵌套组件的 permission
        for (Permission per : permissionList) {
            // 查询当前用户权限的 所有pId（父组件）放在set中
            Integer id = per.getId();
            Integer pId = per.getpId();
            if (pId != null) parentIdSet.add(pId);
        }
        return parentIdSet;
    }

    private Menu generateMenu(List<Menu> subMenus, Menu pMenu) {
        // TODO 父组件可能还会有父组件。先默认此父组件为顶级组件。（正常的应该还有递归调用）

        // 父组件已经是顶级组件了，将当前组件添加到父组件的子组件列表中去。
        subMenus.forEach(subMenu ->
                subMenu.setPath(pMenu.getPath() + subMenu.getPath()));
        List<Menu> children = new ArrayList<>(subMenus);
        pMenu.setChildren(children);
        return pMenu;
    }
}
