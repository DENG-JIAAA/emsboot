package top.dj.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.Log;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.EquVO;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.EquipmentService;
import top.dj.service.LogService;
import top.dj.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/4/26
 */

@Aspect
@Component
@Slf4j
public class LogAspect<T> {
    private static final Logger logger = LoggerFactory.getLogger(Logger.class);
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;
    @Autowired
    private EquipmentService equipmentService;

    @Pointcut("execution(* top.dj.service.UserService.changePwd(..))")

    public void changePwd() {
    }

    @Pointcut("execution(* top.dj.controller.*.addOne*(..))")
    public void addEntity() {
    }

    @Pointcut("execution(* top.dj.controller.BaseController.modifyOneEntity(..))")
    public void modifyEntity() {
    }

    @Pointcut("execution(* top.dj.controller.BaseController.deleteOneEntity(..))")
    public void deleteEntity() {
    }


    public String getNowUserName(HttpServletRequest request) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        assert user != null;
        return user.getLoginName();
    }

    @AfterReturning(pointcut = "changePwd() && args(request, oldPwd, newPwd)", returning = "count", argNames = "request,oldPwd,newPwd,count")
    public void afterChange(HttpServletRequest request, String oldPwd, String newPwd, Integer count) {
        String name = getNowUserName(request);
        Log log = new Log();
        log.setOperate("用户修改密码")
                .setOperateTime(new Timestamp(System.currentTimeMillis()))
                .setOperater(name)
                .setOperand(name);
        logService.save(log);
    }

    @AfterReturning(pointcut = "addEntity() && args(request, t)", returning = "vo", argNames = "request,t,vo")
    public void afterAddEntity(HttpServletRequest request, T t, ResultVO<Boolean> vo) {
        String name = getNowUserName(request);
        boolean isUser = t instanceof User;
        Log log = new Log();
        log.setOperate(isUser ? "添加用户信息" : "添加设备信息")
                .setOperateTime(new Timestamp(System.currentTimeMillis()))
                .setOperater(name)
                .setOperand(isUser ? ((User) t).getLoginName() : ((EquVO) t).getEquName());
        logService.save(log);
    }

    @AfterReturning(pointcut = "modifyEntity() && args(request, t)", returning = "vo", argNames = "request,t,vo")
    public void afterModifyEntity(HttpServletRequest request, T t, ResultVO<Boolean> vo) {
        String name = getNowUserName(request);
        // 目前只涉及到user和equipment两个实体类
        boolean isUser = t instanceof User;
        Log log = new Log();
        log.setOperate(isUser ? "修改用户信息" : "修改设备信息")
                .setOperateTime(new Timestamp(System.currentTimeMillis()))
                .setOperater(name)
                .setOperand(isUser ? ((User) t).getLoginName() : ((Equipment) t).getEquName());
        logService.save(log);
    }

    @Around("deleteEntity()")
    public Object afterDeleteEntity(ProceedingJoinPoint joinPoint) throws Throwable {
        // 方法参数数组
        Object[] args = joinPoint.getArgs();
        String name = getNowUserName((HttpServletRequest) args[0]);
        Integer id = (Integer) args[1];//不确定是删除用户还是设备
        User user = userService.getById(id);
        Equipment equ = equipmentService.getById(id);
        // 执行目标方法之前
        ResultVO<Boolean> vo = (ResultVO<Boolean>) joinPoint.proceed();
        // 执行目标方法之后
        boolean isUser = vo.getMessage().contains("用户");
        Log log = new Log();
        log.setOperate(isUser ? "删除用户信息" : "删除设备信息")
                .setOperateTime(new Timestamp(System.currentTimeMillis()))
                .setOperater(name)
                .setOperand(isUser ? user.getLoginName() : equ.getEquName());
        logService.save(log);
        return vo;
    }


}
