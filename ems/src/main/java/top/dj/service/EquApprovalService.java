package top.dj.service;

import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.VO.EquApprovalVO;
import top.dj.POJO.VO.EquRepairInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EquApprovalService extends MyIService<EquApproval> {

    /**
     * 如果当前用户存在，且已经登录了。
     * 返回 [普通用户/管理员] 的 [申请记录/审批记录]
     *
     * @param request http请求
     * @return 申请记录
     */
    List<EquApprovalVO> list(HttpServletRequest request);

    List<EquApprovalVO> getApplyingList(HttpServletRequest request);

    List<EquApprovalVO> getUsingEqu(HttpServletRequest request);

    Boolean returnOneEquipment(Integer appId);

    List<EquApprovalVO> getReturnedEqu(HttpServletRequest request);

    List<EquApprovalVO> getStoredEqu(HttpServletRequest request);

    List<EquApprovalVO> getRepairingEqu(HttpServletRequest request);

    List<EquApprovalVO> getScrappedEqu(HttpServletRequest request);

    EquApproval show(HttpServletRequest request, Integer appId);

    Boolean pass(Integer appId);

    Boolean startUseEquipment(Integer appId, Long startTime);

    Boolean reject(Integer appId);

    Boolean reApplyUseEquipment(Integer appId);

    Boolean cancelApplication(Integer appId);

    Boolean storeReturnedEqu(Integer appId);

    Boolean storePartReturnedEqu(Integer appId, Integer num);

    Boolean maintainReturnedEqu(Integer appId,Integer num);

    Boolean maintainReturnedEqu(EquRepairInfo equRepairInfo);

    Boolean scrapReturnedEqu(Integer appId);

    Boolean scrapPartReturnedEqu(Integer appId, Integer num);

    Boolean record(Integer appId);
}
