package top.dj.POJO.VO;

import lombok.Data;

/**
 * @author dj
 * @date 2021/1/29
 */
@Data
public class ResultVO<T> {
    /**
     * 响应编码
     * 20000 - 获取信息成功
     * 50014 - 令牌已过期
     * 100 - 请求成功
     * 101 - 请求异常
     * 103 - 未登录
     * 104 - 请求失败
     */
    private Integer code;
    private String message;
    private T data;

    public ResultVO() {
    }

    public ResultVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
