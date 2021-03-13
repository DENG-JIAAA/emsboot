package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.*;
import top.dj.service.EquApprovalService;
import top.dj.service.EquipmentService;
import top.dj.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dj
 * @date 2021/1/12
 */
@RestController
@RequestMapping("/equ")
public class EquipmentController extends BaseController<Equipment> {
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private EquApprovalService equApprovalService;


    @GetMapping("/vo/{id}")
    public EquVO findEquVO(@PathVariable("id") Integer id) {
        return equipmentService.findEquVO(id);
    }

    /*@GetMapping("/vo/{page}/{limit}")
    public DataVO<EquVO> findEquVO(@PathVariable("page") Integer page,
                                   @PathVariable("limit") Integer limit) {
        return equipmentService.findEquVO(page, limit);
    }*/

    /**
     * admin管理员只能查看他所负责的那个实践室的所有设备。
     * 比如：张三管理员（负责实践室C）正在请求获取设备列表，返回的设备都应该满足（equRoom = 3）
     *
     * @param page  当前页
     * @param limit 每一页记录数
     * @return 设备分页数据
     */
    @GetMapping("/vo")
    public ResultVO<DataVO<EquVO>> getEquList(HttpServletRequest request,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("limit") Integer limit) {

        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        DataVO<EquVO> equVO = equipmentService.findEquVO(page, limit, user);
        boolean OK = equVO != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取设备列表成功" : "获取设备列表失败", equVO);
    }

    /**
     * 条件查询，非超级管理员只能查看本实践室的所有设备。
     *
     * @param request    http请求
     * @param equQueryVO 封装了分页信息、和一些设备的查询信息
     * @return 按条件查出的分页数据
     */
    @GetMapping("/vo/query")
    public ResultVO<DataVO<EquVO>> fetchEquListByQuery(HttpServletRequest request, EquQueryVO equQueryVO) {

        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        DataVO<EquVO> equVO = equipmentService.fetchEquListByQuery(user, equQueryVO);
        boolean OK = equVO != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取设备条件查询列表成功" : "获取设备条件查询列表失败", equVO);
    }

    /**
     * 用户申请使用设备
     * 置设备申请状态为，待审核。
     *
     * @param userId 用户id
     * @param equId  设备id
     * @return
     */
    @GetMapping("/apply")
    public ResultVO<Boolean> applyForUseEquipment(@RequestParam("userId") Integer userId,
                                                  @RequestParam("equId") Integer equId) {

        Boolean OK = equipmentService.applyForUseEquipment(userId, equId);
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "申请成功" : "申请失败", OK);
    }

    /**
     * 用户申请使用设备
     * 置设备申请状态为，待审核。
     *
     * @param applyInfo 申请信息
     * @return
     */
    @GetMapping("/applyInfo")
    public ResultVO<Boolean> applyForUseEquipment(ApplyInfo applyInfo) {
        Boolean OK = equipmentService.applyForUseEquipment(applyInfo);
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "申请成功" : "申请失败", OK);
    }

    /**
     * 获取申请记录（全部）
     *
     * @param request http请求
     * @return 申请
     */
    @GetMapping("/applyRecords")
    public ResultVO<List<EquApprovalVO>> showMyApplyRecords(HttpServletRequest request) {
        List<EquApprovalVO> list = equApprovalService.list(request);
        boolean OK = list != null && !list.isEmpty();
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取申请记录成功" : "获取申请记录失败", list);
    }


    /**
     * 获取审批记录
     *
     * @param request http请求
     * @return
     */
    @GetMapping("/approvalRecords")
    public ResultVO<List<EquApprovalVO>> showMyApprovalRecords(HttpServletRequest request) {
        List<EquApprovalVO> list = equApprovalService.list(request);
        boolean OK = list != null && !list.isEmpty();
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取审批记录成功" : "获取审批记录失败", list);
    }
}
