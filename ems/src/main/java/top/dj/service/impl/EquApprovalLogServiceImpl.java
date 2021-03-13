package top.dj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.DO.EquApprovalLog;
import top.dj.mapper.EquApprovalLogMapper;
import top.dj.service.EquApprovalLogService;

/**
 * @author dj
 * @date 2021/3/12
 */

@Service
public class EquApprovalLogServiceImpl extends MyServiceImpl<EquApprovalLogMapper, EquApprovalLog> implements EquApprovalLogService {
    @Autowired
    private EquApprovalLogMapper equApprovalLogMapper;

    /**
     * 用户发起了一条申请 >>>
     * 审核通过 -- 向日志表记录一条申请内容
     * 审核不通过 -- 向日志表记录一条申请内容
     * 设备已归还 -- 向日志表记录一条申请内容
     *
     * @param equApproval
     */
    @Override
    public void recordALog(EquApproval equApproval) {
        EquApprovalLog equApprovalLog = new EquApprovalLog();
        BeanUtils.copyProperties(equApproval, equApprovalLog);
        equApprovalLogMapper.insert(equApprovalLog);
    }
}
