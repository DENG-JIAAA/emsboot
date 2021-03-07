package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.dreamyoung.mprelation.AutoLazy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联角色 和 权限的实体类
 *
 * @author dj
 * @date 2021/3/2
 */

@Data
@AutoLazy
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_role_per")
public class RolePer {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("role_id")
    private Integer roleId;

    @TableField("per_id")
    private Integer perId;

}
