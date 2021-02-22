package top.dj.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当前登录的用户没有权限访问资源时的 处理器
 *
 * @author dj
 * @date 2021/2/9
 */
@Component
public class NoAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("code", 20003);
        objectNode.put("message", "访问失败，您没有权限进行此操作。");

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String str = mapper.writeValueAsString(objectNode);
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }
}
