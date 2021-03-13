package top.dj.POJO.VO;

import lombok.Data;

/**
 * 申请使用设备信息
 * xx用户申请使用xx设备，申请多少个，用来做什么，申请时间为多久，以及备注。
 *
 * @author dj
 * @date 2021/3/10
 */

@Data
public class ApplyInfo {
    private Integer userId;
    private Integer equId;
    private Integer equQuantity;
    private Integer equUseCate;

    private Long equUseTime;
    private String remark;

    public ApplyInfo() {
    }

    public ApplyInfo(Integer userId, Integer equId, Integer equQuantity, Integer equUseCate, Long equUseTime, String remark) {
        this.userId = userId;
        this.equId = equId;
        this.equQuantity = equQuantity;
        this.equUseCate = equUseCate;
        this.equUseTime = equUseTime;
        this.remark = remark;
    }
}
