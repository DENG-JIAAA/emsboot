package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dreamyoung.mprelation.*;
import lombok.AllArgsConstructor;
import top.dj.mapper.EquApprovalMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author dj
 * @date 2021/3/10
 */

@AutoLazy
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("ems_equ_approval_status")
public class EquApprovalStatus implements Serializable {
    private static final long serialVersionUID = -864996757176316897L;

    private Integer id;
    private String status;
    private String remark;

    @TableField(exist = false)
    @ManyToMany(targetEntity = Equipment.class)
    @JoinTable(targetMapper = EquApprovalMapper.class, name = "ems_equ_approval")
    @JoinColumn(name = "id", referencedColumnName = "approval_status_id")
    @JsonIgnore//json忽略目前审批状态的设备
    @InverseJoinColumn(name = "id", referencedColumnName = "equ_id")
    private List<Equipment> equipments;

    public EquApprovalStatus() {
    }

    public EquApprovalStatus(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EquApprovalStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", equipments=" + equipments +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
