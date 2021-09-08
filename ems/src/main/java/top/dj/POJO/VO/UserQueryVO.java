package top.dj.POJO.VO;

import lombok.Data;

/**
 * @author dj
 * @date 2021/3/7
 */

@Data
public class UserQueryVO {
    Integer page;
    Integer limit;

    private Integer id;
    private String loginName;
    private String realName;
    private Integer userRole;
    private Integer userRoom;
}
