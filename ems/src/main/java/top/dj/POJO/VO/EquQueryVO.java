package top.dj.POJO.VO;

import lombok.Data;

/**
 * @author dj
 * @date 2021/3/7
 */
@Data
public class EquQueryVO {
    Integer page;
    Integer limit;

    String equName;
    Integer equCate;
    Integer equRoom;
    Integer equState;
}
