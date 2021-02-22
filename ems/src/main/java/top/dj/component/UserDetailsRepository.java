//package top.dj.component;
//
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author dj
// * @date 2021/2/9
// */
//// https://www.it1352.com/1840125.html
//public class UserDetailsRepository {
//    private final Map<String, UserDetails> users = new HashMap<>();
//
//    public void createUser(UserDetails user) {
//        users.putIfAbsent(user.getUsername(), user);
//    }
//
//    public void updateUser(UserDetails user) {
//        users.put(user.getUsername(), user);
//    }
//
//    public void deleteUser(String username) {
//        users.remove(username);
//    }
//
//    public void changePassword(String oldPwd, String newPwd) {
//        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//        if (currentUser == null) {
//            // 这表明某个地方的编码错误
//            throw new AccessDeniedException("无法更改密码，因为在上下文中找不到当前用户的身份验证对象。");
//        }
//        UserDetails user = users.get(currentUser.getName());
//        if (user == null) {
//            throw new IllegalStateException("当前用户在数据库中不存在。");
//        }
//        // 自行实现具体的更新密码逻辑
//    }
//
//    public boolean userExists(String username) {
//        return users.containsKey(username);
//    }
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return users.get(username);
//    }
//
//}
