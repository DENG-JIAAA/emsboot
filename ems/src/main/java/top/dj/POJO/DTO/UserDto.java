package top.dj.POJO.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.dj.POJO.DO.User;

/**
 * @author dj
 * @date 2021/1/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int code;
    private User user;
}
