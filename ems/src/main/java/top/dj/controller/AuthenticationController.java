package top.dj.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.VO.LoginVO;

import java.util.UUID;

/**
 * Authenticate
 *
 * @author dj
 * @date 2021/2/9
 */
@RestController
@RequestMapping("/auth")

public class AuthenticationController {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    // 注入 SpringSecurity 的认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 创建 token
     */
    @PostMapping("/applyToken")
    public JsonNode applyToken(@RequestBody LoginVO loginVO) {
        ObjectNode tokenNode = MAPPER.createObjectNode();
        // 1、创建 UsernamePasswordAuthenticationToken 对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVO.getLoginName(), loginVO.getLoginPwd());
        // 2、交给认证管理器 AuthenticationManager 进行认证（实则进行验证的是 AuthenticationProvider）
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (null != authenticate) {
            // 认证成功，生成token返回给前端
            String token = UUID.randomUUID().toString();
            tokenNode.put("code", 20000);
            tokenNode.put("message", "登录成功");
            tokenNode.put("token", token);
        } else {
            // 认证失败
            tokenNode.put("code", 20004);
            tokenNode.put("message", "登录失败");
        }
        return tokenNode;
    }

}
