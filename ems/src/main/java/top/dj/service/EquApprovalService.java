package top.dj.service;

import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.VO.EquApprovalVO;

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

    List<EquApprovalVO> getReturnedEqu(HttpServletRequest request);

    EquApproval show(Integer appId);

    Boolean pass(Integer appId);

    Boolean reject(Integer appId);
}
