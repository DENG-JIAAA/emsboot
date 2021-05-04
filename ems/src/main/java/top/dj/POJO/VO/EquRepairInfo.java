package top.dj.POJO.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author DengJia
 * @Date 2021/5/2
 * @Description:
 */

@Data
public class EquRepairInfo {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp approvalTime;//填写维修表的时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp equDowntime;//填写维修表的时间
    private String equName;
    private String equManager;// 送修人
    private String userName;// 借用人
    private Integer maintainQuantity;// 送修数量
    private Integer returnQuantity;// 借用数量
    private String remark;

    public EquRepairInfo() {
    }

    public EquRepairInfo(Integer id, Timestamp approvalTime, Timestamp equDowntime, String equName, String equManager, String userName, Integer maintainQuantity, Integer returnQuantity, String remark) {
        this.id = id;
        this.approvalTime = approvalTime;
        this.equDowntime = equDowntime;
        this.equName = equName;
        this.equManager = equManager;
        this.userName = userName;
        this.maintainQuantity = maintainQuantity;
        this.returnQuantity = returnQuantity;
        this.remark = remark;
    }
}
