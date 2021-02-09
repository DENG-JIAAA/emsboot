package top.dj.POJO.DO;

import lombok.Data;

import java.util.List;

/**
 * @author dj
 * @date 2021/1/8
 */
@Data
public class User2 {
    private List<String> roles;
    private String name;
    private String avatar;
    private String introduction;
}
