package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.EquApproval;
import top.dj.POJO.VO.EquApprovalVO;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.EquApprovalService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dj
 * @date 2021/3/10
 */
@RestController
@RequestMapping("/equApproval")
public class EquApprovalController extends BaseController<EquApproval> {
    @Autowired
    private EquApprovalService equApprovalService;

    @GetMapping("/show/{id}")
    public ResultVO<EquApproval> showOneApproval(@PathVariable("id") Integer appId) {
        EquApproval approval = equApprovalService.show(appId);
        boolean OK = approval != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取记录成功" : "获取记录失败", approval);
    }


    /**
     * 普通用户获取所有申请设备的 审核状态信息
     * 管理员获取所有被申请设备的 审核状态信息
     *
     * @param request http请求，判断用户是 普通用户还是管理员
     * @return 设备审核状态列表信息
     */
    @GetMapping("/applying")
    public ResultVO<List<EquApprovalVO>> showApplyEqu(HttpServletRequest request) {
        List<EquApprovalVO> list = equApprovalService.getApplyingList(request);
        boolean OK = list != null && !list.isEmpty();
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取申请记录成功" : "获取申请记录失败", list);
    }

    /**
     * 普通用户查看申请的设备（使用中）
     *
     * @param request
     * @return
     */
    @GetMapping("/using")
    public ResultVO<List<EquApprovalVO>> showUsingEqu(HttpServletRequest request) {

        List<EquApprovalVO> list = equApprovalService.getUsingEqu(request);
        boolean OK = list != null && !list.isEmpty();
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取申请记录成功" : "获取申请记录失败", list);
    }


    @GetMapping("/returned")
    public ResultVO<List<EquApprovalVO>> returnedEqu(HttpServletRequest request) {
        List<EquApprovalVO> list = equApprovalService.getReturnedEqu(request);
        boolean OK = list != null && !list.isEmpty();
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取申请记录成功" : "获取申请记录失败", list);
    }



    @PostMapping("/pass/{id}")
    public ResultVO<Boolean> passOneApproval(@PathVariable("id") Integer appId) {
        Boolean pass = equApprovalService.pass(appId);
        return new ResultVO<>
                (pass ? 20000 : 20404, pass ? "审核通过" : "审核出错", pass);
    }

    @PostMapping("/reject/{id}")
    public ResultVO<Boolean> rejectOneApproval(@PathVariable("id") Integer appId) {
        Boolean reject = equApprovalService.reject(appId);
        return new ResultVO<>
                (reject ? 20000 : 20404, reject ? "审核驳回" : "审核出错", reject);
    }

    @PutMapping("/remove/{id}")
    public ResultVO<Boolean> removeOneApproval(@PathVariable("id") Integer appId) {
        Boolean remove = equApprovalService.removeById(appId);
        return new ResultVO<>
                (remove ? 20000 : 20404, remove ? "移除审核记录" : "移除出错", remove);
    }
}
