package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.*;
import com.github.dreamyoung.mprelation.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import top.dj.mapper.RolePerMapper;
import top.dj.mapper.UserRoleMapper;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 角色实体类
 *
 * @author dj
 * @date 2021/1/13
 */
@AutoLazy
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@TableName("ems_role")
// 角色实体类
public class Role implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = -4836223776628440502L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;                 // 角色id
    private String roleName;            // 角色英文名称
    private String roleNameZh;          // 角色中文名称
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp roleCreateTime;   // 角色创建时间名字
    private String roleDescription;     // 描述

    @ManyToMany(targetEntity = User.class)
    @JoinTable(targetMapper = UserRoleMapper.class, name = "ems_user_role")
    @JoinColumn(name = "id", referencedColumnName = "role_id")
    @InverseJoinColumn(name = "id", referencedColumnName = "user_id")
    @JsonIgnore
    @TableField(exist = false)
    private List<User> roleUsers;

    @TableField(exist = false)
    @ManyToMany(targetEntity = Permission.class)
    @JoinTable(targetMapper = RolePerMapper.class, name = "ems_role_per")
    @JoinColumn(name = "id", referencedColumnName = "role_id")
    @InverseJoinColumn(name = "id", referencedColumnName = "per_id")
    private List<Permission> rolePers;


    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleNameZh='" + roleNameZh + '\'' +
                ", roleCreateTime=" + roleCreateTime +
                ", roleDescription='" + roleDescription + '\'' +
                ", roleUsers=" + roleUsers +
                ", rolePers=" + rolePers +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNameZh() {
        return roleNameZh;
    }

    public void setRoleNameZh(String roleNameZh) {
        this.roleNameZh = roleNameZh;
    }

    public Timestamp getRoleCreateTime() {
        return roleCreateTime;
    }

    public void setRoleCreateTime(Timestamp roleCreateTime) {
        this.roleCreateTime = roleCreateTime;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public List<User> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(List<User> roleUsers) {
        this.roleUsers = roleUsers;
    }

    public List<Permission> getRolePers() {
        return rolePers;
    }

    public void setRolePers(List<Permission> rolePers) {
        this.rolePers = rolePers;
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }

    public void setAuthority(String authority) {
        this.roleName = authority;
    }

}
