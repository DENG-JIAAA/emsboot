package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer equId;
    private Integer equQuantity;
    private Integer equUseCate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp approvalTime;//用户发出申请的时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp equUseTime;//申请使用设备的时间
    private Integer equDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp equReturnTime;//返还使用设备的时间
    private Integer approvalStatusId;
    private Integer originId;//部分申请记录的来源id
    private String remark;

    public EquApprovalLog() {
    }

    public EquApprovalLog(Integer id, Integer equId) {
        this.id = id;
        this.equId = equId;
    }

    public EquApprovalLog(Integer id, Integer equId, Integer originId) {
        this.id = id;
        this.equId = equId;
        this.originId = originId;
    }
}
