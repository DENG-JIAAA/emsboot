package top.dj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.Log;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.LogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author dj
 * @date 2021/4/29
 */

@RestController
@RequestMapping("/log")
public class LogController extends BaseController<Log> {
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private LogService logService;

    @GetMapping("/page")
    public ResultVO<IPage<Log>> getLogList(HttpServletRequest request,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("limit") Integer limit) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        IPage<Log> logPage = logService.findLogPage(page, limit);
        boolean OK = logPage != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取日志列表成功" : "获取日志列表失败", logPage);
    }

}
