//package top.dj.filter;
//
//import com.alibaba.druid.support.json.JSONUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import top.dj.POJO.DO.User;
//import top.dj.POJO.VO.ResultVO;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author dj
// * @date 2021/2/2
// */
//@Component
//public class LoginFilter implements Filter {
//    @Autowired
//    private RedisTemplate<String, User> redisTemplate;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        // 校验用户登录状态
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        // 获取Headers中的参数
//        String token = request.getHeader("token");
//        token = token == null ? "" : token;
//        // 查询redis中token的剩余时间（若token不存在，将拿到：-1）
//        Long expire = redisTemplate.getExpire(token);
//
//        if (expire > 0) { // 登录状态
//            // 重置token剩余时间（30分钟）
//            redisTemplate.expire(token, 30L, TimeUnit.MINUTES);
//            // 放行
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            // 未登录，响应数据
//            String string = JSONUtils.toJSONString(new ResultVO<String>(103, "未登录", null));
//            response.setContentType("json/text;charset=utf-8");
//            PrintWriter out = response.getWriter();
//            out.write(string);
//        }
//    }
//}
