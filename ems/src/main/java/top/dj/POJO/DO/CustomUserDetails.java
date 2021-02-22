//package top.dj.POJO.DO;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @author dj
// * @date 2021/2/9
// */
//public class CustomUserDetails implements UserDetails {
//    private UserForAuth user;
//
//    public CustomUserDetails() {
//    }
//
//    public CustomUserDetails(final UserForAuth user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        final Set<GrantedAuthority> grantedAuths = new HashSet<>();
//        List<Role> roles = null;
//        if (user != null) {
//            roles = user.getUserRoles();
//        }
//        if (roles != null) {
//            roles.forEach(role -> {
//                grantedAuths.add(new SimpleGrantedAuthority(role.getRoleName()));
//            });
//        }
//        return grantedAuths;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getLoginPwd();
//    }
//
//    @Override
//    public String getUsername() {
//        if (this.user == null) {
//            return null;
//        }
//        return this.user.getLoginName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.getEnabled();
//    }
//
//    @Override
//    public String toString() {
//        return "CustomUserDetails{" +
//                "user=" + user +
//                '}';
//    }
//}
