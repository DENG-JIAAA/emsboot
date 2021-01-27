package top.dj.POJO.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dj
 * @date 2021/1/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto<T> {
    private int code;
    private T t;
}
