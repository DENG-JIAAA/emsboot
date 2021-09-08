package top.dj.service;

import top.dj.POJO.DO.Role;
import top.dj.POJO.VO.RoleVO;

import java.util.List;
import java.util.Map;

public interface RoleService extends MyIService<Role> {
    public List<RoleVO> getRoleVOS();

    Map<Integer, String> getTheMapOfRole();

}
