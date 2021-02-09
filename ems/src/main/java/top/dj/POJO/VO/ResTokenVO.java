package top.dj.POJO.VO;

import lombok.Data;

/**
 * @author dj
 * @date 2021/2/2
 */
@Data
// ResTokenVO用于第一次登录请求，后端会生成一个用户token，封装，返回。
public class ResTokenVO {
    /**
     * 响应编码
     * 20000 - 登录成功（用户存在）
     * 20004 - 登录失败（用户不存在）
     * 101 - 请求异常
     * 103 - 未登录
     * 104 - 请求失败
     */
    private Integer code;
    private String message;
    private String token;

    public ResTokenVO() {
    }

    public ResTokenVO(Integer code, String message, String token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
