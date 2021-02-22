package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dreamyoung.mprelation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.dj.mapper.UserRoleForAuthMapper;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author dj
 * @date 2021/1/8
 */
@Data
@AutoLazy
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_user_for_auth")
// 用户实体类
public class UserForAuth implements UserDetails, Serializable {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;                 //用户id
    private Integer userRoom;           //用户所负责的教学单位设备库id
    private String loginName;           //用户登录名（用户登录名 不可重复）
    private String loginPwd;            //用户登录密码
    private String realName;            //用户的真实姓名（用户真实姓名 可重复）
    private Integer userSex;            //用户性别（1-男；2-女；0-保密）
    private String userPhone;           //手机号
    private String userEmail;           //电子邮箱

    // 多对多
    // https://www.cnblogs.com/dreamyoung/p/12466656.html
    @TableField(exist = false)
    @ManyToMany //多对多默认为延迟加载，即@Lazy(true)或此时不标注
    @JoinTable(targetMapper = UserRoleForAuthMapper.class)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @InverseJoinColumn(name = "role_id", referencedColumnName = "role_id")
    private List<RoleForAuth> userRoles;       //用户角色集合

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;       //创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp loginTime;        //登录时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastLoginTime;    //上一次登录时间
    private Integer loginCount;         //登录次数
    private String userPicture;         //用户的头像（文件名绝对路径）
    @Getter(value = AccessLevel.NONE)   // 不需要生成getter方法
    private Boolean enabled;            //用户激活状态
    private String remark;              //备注

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> auth = new ArrayList<>();
        for (RoleForAuth userRole : userRoles) {
            auth.add(new SimpleGrantedAuthority(userRole.getRoleName()));
        }
        return auth;
    }

    @Override
    public String getPassword() {
        return this.loginPwd;
    }

    @Override
    public String getUsername() {
        return this.loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
