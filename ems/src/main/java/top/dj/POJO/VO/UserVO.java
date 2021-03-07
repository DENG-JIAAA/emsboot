package top.dj.POJO.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import top.dj.POJO.DO.Role;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author dj
 * @date 2021/1/13
 */
@Data
public class UserVO {
    private Integer id;
    private String userRoom; // 用户所负责的教学单位设备库名称
    private String loginName;
    private String loginPwd;
    private String realName;
    private Integer userSex;
    private String userPhone;
    private String userEmail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp loginTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastLoginTime;
    private Integer loginCount;
    private String userPicture;
    private String remark;

    //private List<Role> userRoles;       //用户角色集合
    private List<Role> authorities;
}
