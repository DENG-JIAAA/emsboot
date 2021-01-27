package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/1/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("ems_user")
// 用户实体类
public class User extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Integer id;                 //用户id
    private Integer userRoom;           //用户所负责的教学单位设备库id
    private String loginName;           //用户登录名（用户登录名 不可重复）
    private String loginPwd;            //用户登录密码
    private String realName;            //用户的真实姓名（用户真实姓名 可重复）
    private Integer userSex;            //用户性别（1-男；2-女；0-保密）
    private String userPhone;           //手机号
    private String userEmail;           //电子邮箱
    private Integer userRole;           //用户角色（1-超级管理员；2-教学单位管理员；3-普通用户）
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;       //创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp loginTime;        //登录时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastLoginTime;    //上一次登录时间
    private Integer loginCount;         //登录次数
    private String userPicture;         //用户的头像（文件名绝对路径）
    private String remark;              //备注
}
