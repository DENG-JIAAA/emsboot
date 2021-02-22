package top.dj.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.dj.POJO.VO.ResTokenVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author dj
 * @date 2021/2/15
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println("登录成功");
        response.setStatus(HttpStatus.OK.value());

        ObjectMapper mapper = new ObjectMapper();
        ResTokenVO tokenVO = new ResTokenVO(20000, "登录成功！！", "djosimon-token");

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String str = mapper.writeValueAsString(tokenVO);
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }
}
