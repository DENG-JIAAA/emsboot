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
public class UnLoginHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        if (authException instanceof BadCredentialsException) {
            // 账号或密码错误
            objectNode.put("code", 20004);
            objectNode.put("message", "账号或密码错误，用户不存在。");
        } else {
            objectNode.put("code", 20004);
            objectNode.put("message", "未登录或token无效。（未登录用户正在访问受保护的资源）");
        }

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String str = mapper.writeValueAsString(objectNode);
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }
}
