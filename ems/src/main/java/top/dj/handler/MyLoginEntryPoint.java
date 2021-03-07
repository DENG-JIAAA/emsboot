package top.dj.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登录用户访问受限资源时的 处理器
 *
 * @author dj
 * @date 2021/2/9
 */

@Component
public class MyLoginEntryPoint implements AuthenticationEntryPoint {

    /**
     * 未登录时访问资源，请求会被 FilterSecurityInterceptor 这个过滤器拦截到，然后抛出异常，
     * 这个异常会被 ExceptionTranslationFilter 这个过滤器捕获到，
     * 并最终交给 AuthenticationEntryPoint 接口的 commence 方法处理。
     * 所以处理办法是自定义一个 AuthenticationEntryPoint 的实现类并配置到 SpringSecurity 中。
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        if (authException instanceof BadCredentialsException) {
            // 账号或密码错误
            objectNode.put("code", 20004);
            objectNode.put("message", "账号或密码错误");
        } else {
            objectNode.put("code", 20403);
            objectNode.put("message", "用户未登录（请先完成登录再发起请求访问资源）");
        }

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String str = mapper.writeValueAsString(objectNode);
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }
}
