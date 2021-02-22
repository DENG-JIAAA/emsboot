package top.dj.service;

import com.github.dreamyoung.mprelation.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.dj.POJO.DO.UserForAuth;

public interface UserForAuthService extends IService<UserForAuth>, UserDetailsService {
}
