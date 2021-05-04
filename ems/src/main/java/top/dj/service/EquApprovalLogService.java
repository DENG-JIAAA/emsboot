package top.dj.service;

import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.DO.EquApprovalLog;

import java.util.Set;

public interface EquApprovalLogService extends MyIService<EquApprovalLog> {
    boolean recordLog(EquApproval equApproval);

    /**
     * 获得管理员负责实践室的所有设备的 被借用次数之和
     *
     * @param userId 管理员id
     * @return
     */
    Set<Integer> numOfBorrow(Integer userId);

    Integer quantityOfBorrow(Integer userId);

    Integer useCateScore(Integer userId);

    /**
     * 获得管理员负责实践室的所有设备的 被借用的达标率（借用完后未维修、未报废，直接入库）
     *
     * @param userId 管理员id
     * @return 返回一个百分点（ 达标数/被借用总数 ）
     */
    // Integer equStandardPoint(Integer userId);


}
