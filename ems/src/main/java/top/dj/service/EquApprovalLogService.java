package top.dj.service;

import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.DO.EquApprovalLog;

public interface EquApprovalLogService extends MyIService<EquApprovalLog> {
    void recordALog(EquApproval equApproval);

}
