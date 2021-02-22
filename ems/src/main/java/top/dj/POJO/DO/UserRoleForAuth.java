package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.dreamyoung.mprelation.AutoLazy;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author dj
 * @date 2021/2/15
 */
@Data
@AutoLazy
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("ems_user_role_for_auth")
// 关联用户 和 角色的实体类
public class UserRoleForAuth extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
