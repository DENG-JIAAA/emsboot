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
        boolean OK = list != null;
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
        boolean OK = list != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取申请记录成功" : "获取申请记录失败", list);
    }

    @GetMapping("/return/{id}")
    public ResultVO<Boolean> returnEquipment(@PathVariable("id") Integer appId) {
        Boolean ret = equApprovalService.returnOneEquipment(appId);
        return new ResultVO<>
                (ret ? 20000 : 20404, ret ? "归还成功" : "归还失败", ret);
    }

    /**
     * 管理员查看所有使用者归还的设备
     */
    @GetMapping("/returned")
    public ResultVO<List<EquApprovalVO>> returnedEqu(HttpServletRequest request) {
        List<EquApprovalVO> list = equApprovalService.getReturnedEqu(request);
        boolean OK = list != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取返还记录成功" : "获取返还记录失败", list);
    }

    /**
     * 管理员查看所有使用者归还且已入库的设备
     */
    @GetMapping("/stored")
    public ResultVO<List<EquApprovalVO>> storedEqu(HttpServletRequest request) {
        List<EquApprovalVO> list = equApprovalService.getStoredEqu(request);
        boolean OK = list != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取入库记录成功" : "获取入库记录失败", list);
    }

    @GetMapping("/scrapped")
    public ResultVO<List<EquApprovalVO>> scrappedEqu(HttpServletRequest request) {
        List<EquApprovalVO> list = equApprovalService.getScrappedEqu(request);
        boolean OK = list != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取报废记录成功" : "获取报废记录失败", list);
    }

    @PostMapping("/pass/{id}")
    public ResultVO<Boolean> passOneApproval(@PathVariable("id") Integer appId) {
        Boolean pass = equApprovalService.pass(appId);
        return new ResultVO<>
                (pass ? 20000 : 20404, pass ? "审核通过" : "审核出错", pass);
    }

    @GetMapping("/use/{id}")
    public ResultVO<Boolean> startUseEquipment(@PathVariable("id") Integer appId) {
        Boolean use = equApprovalService.startUseEquipment(appId);
        return new ResultVO<>
                (use ? 20000 : 20404, use ? "开始使用设备成功" : "开始使用设备失败", use);
    }

    @PostMapping("/reject/{id}")
    public ResultVO<Boolean> rejectOneApproval(@PathVariable("id") Integer appId) {
        Boolean reject = equApprovalService.reject(appId);
        return new ResultVO<>
                (reject ? 20000 : 20404, reject ? "审核驳回" : "审核出错", reject);
    }

    @GetMapping("/reApply/{id}")
    public ResultVO<Boolean> reApplyUseEquipment(@PathVariable("id") Integer appId) {
        Boolean reApply = equApprovalService.reApplyUseEquipment(appId);
        return new ResultVO<>
                (reApply ? 20000 : 20404, reApply ? "重新申请成功" : "重新申请失败", reApply);
    }

    @PutMapping("/remove/{id}")
    public ResultVO<Boolean> removeOneApproval(@PathVariable("id") Integer appId) {
        Boolean remove = equApprovalService.removeById(appId);
        return new ResultVO<>
                (remove ? 20000 : 20404, remove ? "移除审核记录" : "移除出错", remove);
    }

    @GetMapping("/cancel/{id}")
    public ResultVO<Boolean> cancelApplication(@PathVariable("id") Integer appId) {
        Boolean cancel = equApprovalService.cancelApplication(appId);
        return new ResultVO<>
                (cancel ? 20000 : 20404, cancel ? "取消申请成功" : "取消申请失败", cancel);
    }

    /**
     * 入库所有
     *
     * @param appId
     * @return
     */
    @PutMapping("/store/{id}")
    public ResultVO<Boolean> storeReturnedEqu(@PathVariable("id") Integer appId) {
        Boolean store = equApprovalService.storeReturnedEqu(appId);
        return new ResultVO<>
                (store ? 20000 : 20404, store ? "入库设备成功" : "入库设备失败", store);
    }

    /**
     * 入库部分
     *
     * @param appId
     * @param num   入库数量
     * @return
     */
    @PutMapping("/store")
    public ResultVO<Boolean> storePartReturnedEqu(@RequestParam("id") Integer appId,
                                                  @RequestParam("num") Integer num) {
        Boolean store = equApprovalService.storePartReturnedEqu(appId, num);
        return new ResultVO<>
                (store ? 20000 : 20404, store ? "入库设备成功" : "入库设备失败", store);
    }

    /**
     * 维修设备 应该先填写维修表单
     */
    @PostMapping("/maintain/{id}")
    public ResultVO<Boolean> maintainReturnedEqu(@PathVariable("id") Integer appId) {
        Boolean maintain = equApprovalService.maintainReturnedEqu(appId);
        return new ResultVO<>
                (maintain ? 20000 : 20404, maintain ? "维修表单提交成功" : "维修表单提交失败", maintain);
    }

    /**
     * 维修用户返还的设备
     * 1、全数维修 -- 将状态置为 “维修中”，用户返还数量不变
     * 2、部分维修 -- 用户已归还页面状态不变，用户返还数量减去报修数量，设备维修中页面增加对应数量的维修记录
     * 3、维修后可选择 -- 部分入库、全数入库、部分报废、全数报废
     */
    @PostMapping("/maintain")
    public ResultVO<Boolean> maintainPartReturnedEqu() {


//        Boolean maintain = equApprovalService.maintainReturnedEqu(appId);
//        return new ResultVO<>
//                (maintain ? 20000 : 20404, maintain ? "维修表单提交成功" : "维修表单提交失败", maintain);
        return null;
    }

    /**
     * 报废所有
     *
     * @param appId
     * @return
     */
    @PutMapping("/scrap/{id}")
    public ResultVO<Boolean> scrapReturnedEqu(@PathVariable("id") Integer appId) {
        Boolean scrap = equApprovalService.scrapReturnedEqu(appId);
        return new ResultVO<>
                (scrap ? 20000 : 20404, scrap ? "报废设备成功" : "报废设备失败", scrap);
    }

    /**
     * 报废部分
     *
     * @param appId
     * @param num   报废数量
     * @return
     */
    @PutMapping("/scrap")
    public ResultVO<Boolean> scrapPartReturnedEqu(@RequestParam("id") Integer appId,
                                                  @RequestParam("num") Integer num) {
        // Boolean scrap = equApprovalService.scrapReturnedEqu(appId);
        Boolean scrap = equApprovalService.scrapPartReturnedEqu(appId, num);
        return new ResultVO<>
                (scrap ? 20000 : 20404, scrap ? "报废设备成功" : "报废设备失败", scrap);
    }

    @PostMapping("/record/{id}")
    public ResultVO<Boolean> recordALog(@PathVariable("id") Integer appId) {
        Boolean record = equApprovalService.record(appId);
        return new ResultVO<>
                (record ? 20000 : 20404, record ? "记录成功" : "记录失败", record);
    }
}
