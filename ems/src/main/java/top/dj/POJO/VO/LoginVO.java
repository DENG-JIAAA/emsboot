package top.dj.POJO.VO;

import lombok.Data;

/**
 * @author dj
 * @date 2021/1/29
 */
@Data
public class LoginVO {
    String loginName;
    String loginPwd;

    public LoginVO() {
    }

    public LoginVO(String loginName, String loginPwd) {
        this.loginName = loginName;
        this.loginPwd = loginPwd;
    }
}
