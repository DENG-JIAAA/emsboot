package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/1/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("ems_permission")
// 权限实体类
public class PermissionForAuth extends BaseDO implements GrantedAuthority {
    @TableId(type = IdType.AUTO)
    private Integer id;                 //权限id
    private String perName;             //权限名字
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp perCreateTime;    //权限创建时间
    private String perDesc;             //权限描述

    @Override
    public String getAuthority() {
        return null;
    }
}
