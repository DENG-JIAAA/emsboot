package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.EquApprovalVO;
import top.dj.enumeration.ApplyStatus;
import top.dj.mapper.*;
import top.dj.service.EquApprovalLogService;
import top.dj.service.EquApprovalService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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
    private EquUseCateMapper equUseCateMapper;
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

    /**
     * 用户归还设备，应该向申请记录添加一条日志记录。保存，供绩效使用。
     * ems_equ_approval --> ems_equ_approval_log
     */
    @Override
    public Boolean returnOneEquipment(Integer appId) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        int count = 0;
        if (approval != null) {
            Integer statusId = approval.getApprovalStatusId();
            // 将设备状态id：5（设备使用中）置为6（设备已归还）
            approval.setEquReturnTime(new Timestamp(System.currentTimeMillis()));
            approval.setApprovalStatusId(statusId == 5 ? 6 : statusId);
            count = equApprovalMapper.updateById(approval);
        }
        return approval != null && count == 1;
    }

    /**
     * 获取用户归还的设备 -- 已归还 -- 已入库 -- 维修中 -- 已报废
     *
     * @param request
     * @return
     */
    @Override
    public List<EquApprovalVO> getReturnedEqu(HttpServletRequest request) {
        // return getStatusEquList(request, RETURNED, STORED, MAINTENANCE, SCRAPPED);
        return getStatusEquList(request, RETURNED, MAINTENANCE);
    }

    @Override
    public List<EquApprovalVO> getStoredEqu(HttpServletRequest request) {
        return getStatusEquList(request, STORED);
    }

    @Override
    public List<EquApprovalVO> getScrappedEqu(HttpServletRequest request) {
        return getStatusEquList(request, SCRAPPED);
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
        EquApproval approval = equApprovalMapper.selectById(appId);
        Integer statusId = approval.getApprovalStatusId();
        // 如果当前状态是 -- 待审核，点击查看就将审核状态为 -- 审核中
        approval.setApprovalStatusId(statusId == 1 ? 2 : statusId);
        if (statusId != 1) return approval;
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
        // equApprovalLogService.recordLog(approval);
        // 设置审核状态为：审核通过（删除申请记录）
        int count = 0;
        if (approval != null) {
            approval.setApprovalStatusId(3);
            count = equApprovalMapper.updateById(approval);
        }
        return approval != null && count == 1;
    }

    /**
     * 普通用户审核通过 开始使用设备
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean startUseEquipment(Integer appId) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        int count = 0;
        if (approval != null) {
            Integer statusId = approval.getApprovalStatusId();
            // 设备审核通过，用户可以使用设备。将状态id置为: 5 -- 设备使用中
            approval.setApprovalStatusId(statusId == 3 ? 5 : statusId);
            count = equApprovalMapper.updateById(approval);
        }
        return approval != null && count == 1;
    }

    @Override
    public Boolean reject(Integer appId) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        // equApprovalLogService.recordALog(approval);
        int i = 0, j = 0;
        if (approval != null) {
            approval.setApprovalStatusId(4);
            i = equApprovalMapper.updateById(approval);
            Equipment e = equipmentMapper.selectById(approval.getEquId());
            if (e != null) {
                // 设置审核状态为：审核不通过，应该还原设备数量。
                e.setEquQuantity(e.getEquQuantity() + approval.getEquQuantity());
                j = equipmentMapper.updateById(e);
            }
        }
        return approval != null && i == 1 && j == 1;
    }

    /**
     * 普通用户审核未通过，点击重新申请
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean reApplyUseEquipment(Integer appId) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        int i = 0, j = 0;
        if (approval != null) {
            Integer statusId = approval.getApprovalStatusId();
            // 重新申请：将这个条申请的状态id，从4（不通过）置为1（待审核）即可。
            approval.setApprovalStatusId(statusId == 4 ? 1 : statusId);
            i = equApprovalMapper.updateById(approval);
            Equipment e = equipmentMapper.selectById(approval.getEquId());
            if (e != null) {
                e.setEquQuantity(e.getEquQuantity() - approval.getEquQuantity());
                j = equipmentMapper.updateById(e);
            }
        }
        return approval != null && i == 1 && j == 1;
    }

    /**
     * 取消，删除一条申请
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean cancelApplication(Integer appId) {
        int count = equApprovalMapper.deleteById(appId);
        return count == 1;
    }

    /**
     * 归还的设备、维修后的设备 都可以入库
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean storeReturnedEqu(Integer appId) {
        // num为空，全部入库
        return store(appId, null);
    }

    @Override
    public Boolean storePartReturnedEqu(Integer appId, Integer num) {
        return store(appId, num);
    }


    /**
     * 入库设备，num为空的话即视为全部入库
     * 对于部分入库的情况，应当复制一条请求使用记录（改变设备数量为入库数量）
     *
     * @param appId 借用记录id
     * @param num   需要入库的设备数量
     * @return
     */
    public Boolean store(Integer appId, @Nullable Integer num) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        int i = 0, j = 0;
        if (approval != null) {
            Integer equId = approval.getEquId();
            Integer equQuantity = approval.getEquQuantity();
            Integer statusId = approval.getApprovalStatusId();
            // 用户归还的设备一切正常，管理员将其入库。
            if (equQuantity.equals(num)) {
                // 全部入库，设置新状态，数量不变
                approval.setApprovalStatusId(statusId == 6 || statusId == 8 ? 7 : statusId);
            } else {
                // 部分入库（复制一条请求使用记录）
                // 1、改变已返还的数量
                approval.setEquQuantity(num == null ? equQuantity : (equQuantity - num));
                // 2、设置指定数量的已入库（生成一个拷贝版，并将拷贝版的parentId设为当前approval的id）
                EquApproval copyApp = new EquApproval();
                copyProperties(approval, copyApp);
                copyApp.setApprovalStatusId(7);
                copyApp.setEquQuantity(num);
                copyApp.setId(null);
                // 3、设置父id
                copyApp.setParentId(appId);
                equApprovalMapper.insert(copyApp);
            }
            i = equApprovalMapper.updateById(approval);

            Equipment equipment = equipmentMapper.selectById(equId);
            equipment.setEquQuantity(equipment.getEquQuantity() + (num == null ? equQuantity : num));
            j = equipmentMapper.updateById(equipment);
        }
        return approval != null && i == 1 && j == 1;
    }

    /**
     * 维修成功 --> 入库
     * 维修失败 --> 报废
     * 维修 -- 入库 -- 报废，三者都要记录
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean maintainReturnedEqu(Integer appId) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        boolean record = false;
        int count = 0;
        if (approval != null) {
            Integer statusId = approval.getApprovalStatusId();
            approval.setApprovalStatusId(statusId == 6 ? 8 : statusId);
            count = equApprovalMapper.updateById(approval);
            record = equApprovalLogService.recordLog(approval);
        }
        return approval != null && count == 1 && record;
    }

    /**
     * 已归还的设备、维修后的设备 都可以报废
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean scrapReturnedEqu(Integer appId) {
        return scrap(appId, null);
    }

    @Override
    public Boolean scrapPartReturnedEqu(Integer appId, Integer num) {
        return scrap(appId, num);
    }

    public Boolean scrap(Integer appId, @Nullable Integer num) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        int count = 0;
        if (approval != null) {
            Integer equId = approval.getEquId();
            Integer statusId = approval.getApprovalStatusId();
            Integer equQuantity = approval.getEquQuantity();
            // 用户归还的设备损坏严重，置为 设备已报废。
            if (equQuantity.equals(num)) {
                // 管理员报废所有返还的设备，需要修改状态
                approval.setApprovalStatusId(statusId == 6 || statusId == 8 ? 9 : statusId);
            } else {
                // 管理员报废部分返还设备，状态保持不变，将数量更新一下即可
                approval.setEquQuantity(num == null ? equQuantity : (equQuantity - num));
                EquApproval copyApp = new EquApproval();
                copyProperties(approval, copyApp);
                copyApp.setApprovalStatusId(9);
                copyApp.setEquQuantity(num);
                copyApp.setId(null);
                // 设置父id
                copyApp.setParentId(appId);
                equApprovalMapper.insert(copyApp);
            }
            count = equApprovalMapper.updateById(approval);
        }
        return approval != null && count == 1;
    }

    /**
     * 完成一条借用的记录（ 入库、报废设备后方可执行记录操作 ）
     * 需要首先判断appId的parentId是否为null
     * ---
     * 部分入库，同时需要完成记录的情况
     * 1、将部分入库的数据在页面上删除，且保证记录在数据库
     * 2、未进行操作的数据保留在页面，更新数据
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean record(Integer appId) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        boolean record = false;
        if (approval != null) {
            // thisId -- 当前部分设备的记录id
            Integer thisId = approval.getId();
            // partQuantity -- 部分设备的数量
            Integer partQuantity = approval.getEquQuantity();
            // parentId -- 用户借用记录的id
            Integer parentId = approval.getParentId();
            // statusId -- 当前设备的状态
            Integer statusId = approval.getApprovalStatusId();
            if (parentId != null) {
                // 记录父申请
                equApprovalMapper.deleteById(thisId);
                return record(parentId, partQuantity, statusId);
            }
            // 运行到此处说明，记录的是所有（没有父id），log数据表中，1代表正常入库设备，2代表损坏报废设备。
            approval.setApprovalStatusId(statusId.equals(7) ? 1 : 2);
            record = equApprovalLogService.recordLog(approval);
        }
        return approval != null && record;
    }

    /**
     * 记录部分数据
     *
     * @param appId 部分借用记录的id
     * @param num   部分借用的数量
     * @return
     */
    public Boolean record(Integer appId, Integer num) {
        EquApproval approval = equApprovalMapper.selectById(appId);
        Boolean record = false;
        if (approval != null) {
            Integer statusId = approval.getApprovalStatusId();
            if (statusId.equals(7)) {
                System.out.println("入库");
            }
//            approval.setEquQuantity();
        }
        return null;
    }

    /**
     * @param logId    记录id
     * @param num      指定记录在册的设备数
     * @param statusId 这些设备的状态？正常--入库 / 损坏--报废
     * @return
     */
    public Boolean record(Integer logId, Integer num, Integer statusId) {
        // approval -- 当前准备记录在册的申请记录，需要修改的有
        // 1、数量。2、状态（1-正常 / 2-不正常）。3、根据statusId入参来判断是入库的记录还是报废的记录
        EquApproval approval = equApprovalMapper.selectById(logId);
        // 入库操作,不等于7（等于9）就是报废操作
        boolean store = statusId.equals(7);
        approval.setEquQuantity(num);
        // log记录中 1--入库 2--报废
        approval.setApprovalStatusId(store ? 1 : 2);
        return equApprovalLogService.recordLog(approval);
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
        approvalVO.setEquUseCate(equUseCateMapper.selectById(approval.getEquUseCate()).getUseCate());
        approvalVO.setApprovalStatusName(equApprovalStatusMapper.selectById(approval.getApprovalStatusId()).getStatus());
        return approvalVO;
    }
}
