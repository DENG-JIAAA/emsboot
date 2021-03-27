package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.EquApprovalLogService;

/**
 * @author dj
 * @date 2021/3/17
 */

@RestController
@RequestMapping("/equApprovalLog")
public class EquApprovalLogController {
    @Autowired
    private EquApprovalLogService equApprovalLogService;

    /**
     * 获取当前用户所在设备库的所有设备被借使用的次数。（数据库一条记录算一次）
     *
     * @param userId 当前发起请求的用户id
     * @return
     */
    @GetMapping("/total/{userId}")
    public ResultVO<Integer> getTheNumOfBorrow(@PathVariable("userId") Integer userId) {
        Integer num = equApprovalLogService.numOfBorrow(userId);
        return new ResultVO<>(20000, "成功", num);
    }


}
