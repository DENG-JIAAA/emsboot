package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.EquApprovalVO;
import top.dj.enumeration.ApplyStatus;
import top.dj.mapper.EquApprovalMapper;
import top.dj.mapper.EquApprovalStatusMapper;
import top.dj.mapper.EquipmentMapper;
import top.dj.mapper.UserMapper;
import top.dj.service.EquApprovalLogService;
import top.dj.service.EquApprovalService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;
import static top.dj.enumeration.ApplyStatus.*;

/**
 * @author dj
 * @date 2021/3/10
 */
@Service
public class EquApprovalServiceImpl extends MyServiceImpl<EquApprovalMapper, EquApproval> implements EquApprovalService {
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private EquApprovalMapper equApprovalMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private EquApprovalStatusMapper equApprovalStatusMapper;
    @Autowired
    private EquApprovalLogService equApprovalLogService;

    @Override
    public List<EquApprovalVO> list(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        List<EquApproval> approvals = null;
        if (user != null) {
            // 1、用户为普通用户，检索他申请的所有设备记录。
            // 2、用户为管理员，检索他管理设备的被申请记录。
            approvals = AdminOrSuper(user) ? adminUserApps(user.getId()) : plainUserApps(user.getId());
        }
        // 转换成前端适应的VO再返回
        if (approvals != null) return convert(approvals, new ArrayList<EquApprovalVO>());
        return new ArrayList<>();
    }

    @Override
    public List<EquApprovalVO> getApplyingList(HttpServletRequest request) {
        return getStatusEquList(request, PENDING, APPROVAL, PASS, REJECT);
    }

    @Override
    public List<EquApprovalVO> getUsingEqu(HttpServletRequest request) {
        return getStatusEquList(request, USING);
    }

    @Override
    public List<EquApprovalVO> getReturnedEqu(HttpServletRequest request) {
        return getStatusEquList(request, RETURNED);
    }

    public List<EquApprovalVO> getStatusEquList(HttpServletRequest request, ApplyStatus... status) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        Integer index = status.length == 1 ? status[0].getIndex() : null;

        List<Integer> indexs = new ArrayList<>();
        if (index == null) {
            for (ApplyStatus one : status) {
                indexs.add(one.getIndex());
            }
        }

        List<EquApprovalVO> statusEquList = new ArrayList<>();
        List<EquApproval> approvals;
        approvals = user == null ?
                null : AdminOrSuper(user) ? adminUserApps(user.getId()) : plainUserApps(user.getId());
        if (approvals != null) {
            approvals.forEach(app -> {
                Integer statusId = app.getApprovalStatusId();
                if (!indexs.isEmpty() && indexs.contains(statusId))
                    statusEquList.add(convert(app, new EquApprovalVO()));
                if (statusId.equals(index)) statusEquList.add(convert(app, new EquApprovalVO()));
            });
        }
        return statusEquList;
    }

    @Override
    public EquApproval show(Integer appId) {
        EquApproval approval = new EquApproval(appId);
        // 设置审核状态为：审核中
        approval.setApprovalStatusId(2);
        equApprovalMapper.updateById(approval);
        return equApprovalMapper.selectById(appId);
    }

    /**
     * 审核通过，应该向申请记录添加一条日志记录。保存，供绩效使用。
     * ems_equ_approval --> ems_equ_approval_log
     */
    @Override
    public Boolean pass(Integer appId) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        equApprovalLogService.recordALog(approval);
        // 设置审核状态为：审核通过（删除申请记录）
        int count = 0;
        if (approval != null) {
            approval.setApprovalStatusId(3);
            count = equApprovalMapper.updateById(approval);
        }
        return approval != null && count == 1;
    }

    /**
     * 审核驳回，应该向申请记录添加一条日志记录。保存，供绩效使用。
     * ems_equ_approval --> ems_equ_approval_log
     */
    @Override
    public Boolean reject(Integer appId) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        equApprovalLogService.recordALog(approval);
        // 设置审核状态为：审核不通过，应该还原设备数量（删除申请记录）
        int count = 0;
        if (approval != null) {
            approval.setApprovalStatusId(4);
            equApprovalMapper.updateById(approval);
            Equipment e = equipmentMapper.selectById(approval.getEquId());
            if (e != null) {
                e.setEquQuantity(e.getEquQuantity() + approval.getEquQuantity());
                count = equipmentMapper.updateById(e);
            }
        }
        return approval != null && count == 1;
    }

    public boolean AdminOrSuper(User user) {
        // authorities -- 用户的权限列表
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // role --- 用户的角色列表
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return roles.contains("ROLE_ADMIN") || roles.contains("ROLE_SUPERADMIN");
    }

    public List<EquApproval> adminUserApps(Integer adminUserId) {
        Equipment equipment = new Equipment();
        equipment.setEquUser(adminUserId);
        // equipments -- 所有被adminUserId这个管理员管理的设备
        List<Equipment> equipmentList = equipmentMapper.selectList(new QueryWrapper<>(equipment));
        // approvalList -- 所有被申请的设备
        List<EquApproval> approvalList = equApprovalMapper.selectList(null);
        List<EquApproval> approvals = new ArrayList<>();
        // 将所有申请的且由此管理员管理的设备添加进approvals列表
        approvalList.forEach(a -> equipmentList.forEach(e -> {
            if (e.getId().equals(a.getEquId())) approvals.add(a);
        }));
        return approvals;
    }

    public List<EquApproval> plainUserApps(Integer plainUserId) {
        Wrapper<EquApproval> wrapper = new QueryWrapper<>(new EquApproval(null, plainUserId));
        return equApprovalMapper.selectList(wrapper);
    }

    public List<EquApprovalVO> convert(List<EquApproval> approvals, List<EquApprovalVO> approvalVOS) {
        approvals.forEach(approval ->
                approvalVOS.add(convert(approval, new EquApprovalVO())));
        return approvalVOS;
    }

    public EquApprovalVO convert(EquApproval approval, EquApprovalVO approvalVO) {
        // 拷贝相同的
        copyProperties(approval, approvalVO);
        // 转换不同的
        approvalVO.setUserName(userMapper.selectById(approval.getUserId()).getRealName());
        approvalVO.setEquName(equipmentMapper.selectById(approval.getEquId()).getEquName());
        approvalVO.setApprovalStatusName(equApprovalStatusMapper.selectById(approval.getApprovalStatusId()).getStatus());
        return approvalVO;
    }
}
