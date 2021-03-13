package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dreamyoung.mprelation.AutoLazy;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/3/12
 */

@Data
@AutoLazy
@AllArgsConstructor
@TableName("ems_equ_approval_log")
public class EquApprovalLog implements Serializable {
    private static final long serialVersionUID = 8267788634353466204L;

    private Integer id;
    private Integer userId;
    private Integer equId;
    private Integer equQuantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp approvalTime;//用户发出申请的时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp equUseTime;//申请使用设备的时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp equReturnTime;//返还使用设备的时间
    private Integer approvalStatusId;

    public EquApprovalLog() {
    }
}
