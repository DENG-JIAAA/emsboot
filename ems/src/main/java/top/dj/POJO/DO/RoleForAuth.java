package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dreamyoung.mprelation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import top.dj.mapper.UserRoleForAuthMapper;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author dj
 * @date 2021/1/13
 */
@Data
@AutoLazy
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_role_for_auth")
// 角色实体类
public class RoleForAuth implements GrantedAuthority {
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;                 // 角色id
    private String roleName;            // 角色英文名称
    private String roleNameZh;          // 角色中文名称

    // 多对多
    @TableField(exist = false)
    @ManyToMany
    @JoinTable(targetMapper = UserRoleForAuthMapper.class)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @InverseJoinColumn(name = "user_id", referencedColumnName = "user_id")
    private List<UserForAuth> userList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp roleCreateTime;   // 角色创建时间名字
    private String roleDescription;     // 描述

    public RoleForAuth(String roleName) {
        Assert.hasText(roleName, "A granted authority textual representation is required");
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }

}
