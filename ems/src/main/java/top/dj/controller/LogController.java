package top.dj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DO.Log;
import top.dj.POJO.DO.User;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.LogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 用于日志的分页数据
     *
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/page")
    public ResultVO<IPage<Log>> getLogList(HttpServletRequest request,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("limit") Integer limit) {
        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        IPage<Log> logPage = logService.findLogPage(user, page, limit);
        boolean OK = logPage != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取日志列表成功" : "获取日志列表失败", logPage);
    }

    /**
     * 用于用户的部分记录数据 展示5条足够
     *
     * @param request
     * @param num     获取数量
     * @return
     */
    @GetMapping("/limit/{num}")
    public ResultVO<List<Log>> getLogLimit(HttpServletRequest request, @PathVariable("num") Integer num) {

        User user = redisTemplate.opsForValue().get(request.getHeader("token"));
        // 获取用户最近的几条操作日志数据
        List<Log> logList = logService.getUserRecentLogs(user, num);
        boolean OK = logList != null;
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取用户日志列表成功" : "获取用户日志列表失败", logList);
    }


}
