//package top.dj.component;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 用户验证管理器
// *
// * @author dj
// * @date 2021/2/25
// */
//@Component
//public class MyAuthenticationManager implements AuthenticationManager {
//
//    // 创建自定义的令牌存储器
//    private static Map<String, String> myTockenMap = new HashMap<String, String>();
//
//    public MyAuthenticationManager() {
//        System.out.println("实例化用户验证管理器...");
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        return myAuthenticate(authentication);
//    }
//
//    public Authentication systemAuthenticate(Authentication authentication) throws AuthenticationException {
//        // 获取传递的参数：登录的用户名和登录的密码
//        System.out.println("用户名:" + authentication.getName());// 等同于authentication.getPrincipal()
//        System.out.println(authentication);
//        // UsernamePasswordAuthenticationToken@53f00533发现当前的authentication其实就是UsernamePasswordAuthenticationToken对象
//        System.out.println("密碼：" + authentication.getCredentials());
//        System.out.println("isAuthenticated():" + authentication.isAuthenticated());
//        return new UsernamePasswordAuthenticationToken("admin", "123465");
//    }
//
//    // 出现500错误，通过一个存放的map解决两次传递的问题，和密码为null的问题
//    public Authentication myAuthenticate(Authentication authentication) throws AuthenticationException {
//        // 简化上面的操作
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//        String loginName = token.getName();// 或者 token.getPrincipal();获取用户名
//        String pwd = myTockenMap.get(loginName);
//        String loginPwd = null;
//        if (pwd == null) {
//            loginPwd = token.getCredentials().toString();
//            System.out.println("登陆用户名：" + loginName);
//            System.out.println("登录用户密码" + loginPwd);
//            // 执行验证
//            if ("admin".equals(loginName) && "123456".equals(loginPwd)) {
//                // 设置登录后的令牌
//                myTockenMap.put(loginName, loginPwd);
//                // 表示必须创建新的令牌，并设置状态
//                UsernamePasswordAuthenticationToken checkToken = new UsernamePasswordAuthenticationToken(loginName,
//                        loginPwd);// 當前的authenticated从创建就被设置为true
//                /*
//                 * token.setAuthenticated(true);//设置当前的令牌已校验 return token;
//                 */// 这里返回token会发生nullPointException 异常，
//                // 或者Once created you cannot set this token to authenticated. Create a new
//                // instance using the constructor which takes a GrantedAuthority list will mark
//                // this as authenticated.原因：父类中的setAuthenticated中如果设置为true其中就抛出了异常
//                return checkToken;
//            } else {
//                // 返回不为null，表示允许通过
//                return null;// 如果返回null表示验证没有通过
//            }
//
//        } else {
//            return new UsernamePasswordAuthenticationToken(loginName, pwd);
//        }
//    }
//}
