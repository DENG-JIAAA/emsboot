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
     * 20000 - 操作数据成功
     * 20404 - 操作数据失败
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
}
