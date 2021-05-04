package top.dj.POJO.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/3/10
 */
@Data
public class EquApprovalVO {
    private Integer id;
    private String userName;
    private String equName;
    private Integer equQuantity;
    private String equUseCate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp approvalTime;//用户发出申请的时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp equUseTime;//申请使用设备的时间
    private Integer equDay;//使用设备的时间（最长31天）
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp equReturnTime;//返还使用设备的时间

    private String approvalStatusName;
    private String remark;
}
