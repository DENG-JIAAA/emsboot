package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dreamyoung.mprelation.AutoLazy;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/3/10
 */

@AutoLazy
@AllArgsConstructor
@TableName("ems_equ_approval")
public class EquApproval implements Serializable {
    private static final long serialVersionUID = 76071882241262276L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("equ_id")
    private Integer equId;

    @TableField("equ_quantity")
    private Integer equQuantity;

    @TableField("equ_use_cate")
    private Integer equUseCate;//借用的设备将用于什么方向？

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp approvalTime;//用户发出申请的时间

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp equUseTime;//申请使用设备的时间

    @TableField("equ_day")
    private Integer equDay;//使用设备的时间（最长31天）

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp equReturnTime;//返还使用设备的时间

    @TableField("approval_status_id")
    private Integer approvalStatusId;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("remark")
    private String remark;

    public EquApproval() {
    }

    public EquApproval(Integer id) {
        this.id = id;
    }

    public EquApproval(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EquApproval{" +
                "id=" + id +
                ", userId=" + userId +
                ", equId=" + equId +
                ", equQuantity=" + equQuantity +
                ", equUseCate=" + equUseCate +
                ", approvalTime=" + approvalTime +
                ", equUseTime=" + equUseTime +
                ", equDay=" + equDay +
                ", equReturnTime=" + equReturnTime +
                ", approvalStatusId=" + approvalStatusId +
                ", parentId=" + parentId +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEquId() {
        return equId;
    }

    public void setEquId(Integer equId) {
        this.equId = equId;
    }

    public Integer getEquQuantity() {
        return equQuantity;
    }

    public void setEquQuantity(Integer equQuantity) {
        this.equQuantity = equQuantity;
    }

    public Integer getEquUseCate() {
        return equUseCate;
    }

    public void setEquUseCate(Integer equUseCate) {
        this.equUseCate = equUseCate;
    }

    public Timestamp getApprovalTime() {
        return approvalTime;
    }

    public EquApproval setApprovalTime(Timestamp approvalTime) {
        this.approvalTime = approvalTime;
        return this;
    }

    public Timestamp getEquUseTime() {
        return equUseTime;
    }

    public EquApproval setEquUseTime(Timestamp equUseTime) {
        this.equUseTime = equUseTime;
        return this;
    }

    public Integer getEquDay() {
        return equDay;
    }

    public void setEquDay(Integer equDay) {
        this.equDay = equDay;
    }

    public Timestamp getEquReturnTime() {
        return equReturnTime;
    }

    public void setEquReturnTime(Timestamp equReturnTime) {
        this.equReturnTime = equReturnTime;
    }

    public Integer getApprovalStatusId() {
        return approvalStatusId;
    }

    public void setApprovalStatusId(Integer approvalStatusId) {
        this.approvalStatusId = approvalStatusId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
