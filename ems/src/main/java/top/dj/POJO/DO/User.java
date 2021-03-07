package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dreamyoung.mprelation.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.dj.mapper.UserRoleMapper;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author dj
 * @date 2021/1/8
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false)

@AutoLazy
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("ems_user")
// 用户实体类
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1015606638014850163L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;                 //用户id
    private Integer userRoom;           //用户所负责的教学单位设备库id
    private String loginName;           //用户登录名（用户登录名 不可重复）
    private String loginPwd;            //用户登录密码
    private String realName;            //用户的真实姓名（用户真实姓名 可重复）
    private Integer userSex;            //用户性别（1-男；2-女；0-保密）
    private String userPhone;           //手机号
    private String userEmail;           //电子邮箱

    // private Integer userRole;           //用户角色（1-超级管理员；2-教学单位管理员；3-普通用户）

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;       //创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp loginTime;        //登录时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastLoginTime;    //上一次登录时间
    private Integer loginCount;         //登录次数
    private String userPicture;         //用户的头像（文件名绝对路径）
    private String remark;              //备注

    // 多对多
    // https://www.cnblogs.com/dreamyoung/p/12466656.html
    // @JsonIgnore
    @ManyToMany(targetEntity = Role.class) //多对多默认为延迟加载，即@Lazy(true)或此时不标注
    // @JoinTable 一般和 @ManyToMany 使用，处理多对多关系，需要两个 Entity 有中间关系表。
    // https://blog.csdn.net/Hommiee/article/details/106466838
    @JoinTable(targetMapper = UserRoleMapper.class, name = "ems_user_role")
    /*@JoinTable(
            targetMapper = UserRoleMapper.class,
            name = "ems_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )*/
    // @JoinColumn 用来指定中间表中关联自己ID的字段
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    // @InverseJoinColumn 用来指定中间表中关联对方ID的字段
    @InverseJoinColumn(name = "id", referencedColumnName = "role_id")
    //@JsonIgnore
    //@JsonDeserialize
    @TableField(exist = false)
    private List<Role> authorities;       //用户角色集合

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, Integer userRoom, String loginName, String loginPwd, String realName, Integer userSex, String userPhone, String userEmail, Timestamp createTime, Timestamp loginTime, Timestamp lastLoginTime, Integer loginCount, String userPicture, String remark, List<Role> authorities) {
        this.id = id;
        this.userRoom = userRoom;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.realName = realName;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.createTime = createTime;
        this.loginTime = loginTime;
        this.lastLoginTime = lastLoginTime;
        this.loginCount = loginCount;
        this.userPicture = userPicture;
        this.remark = remark;
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userRoom=" + userRoom +
                ", loginName='" + loginName + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", realName='" + realName + '\'' +
                ", userSex=" + userSex +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", createTime=" + createTime +
                ", loginTime=" + loginTime +
                ", lastLoginTime=" + lastLoginTime +
                ", loginCount=" + loginCount +
                ", userPicture='" + userPicture + '\'' +
                ", remark='" + remark + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserRoom() {
        return userRoom;
    }

    public void setUserRoom(Integer userRoom) {
        this.userRoom = userRoom;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> auth = new HashSet<>();
        authorities.forEach(role ->
                auth.add(new SimpleGrantedAuthority(role.getAuthority())));
        return auth;
    }

    @Override
    public String getPassword() {
        return loginPwd;
    }

    @Override
    public String getUsername() {
        return loginName;
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
