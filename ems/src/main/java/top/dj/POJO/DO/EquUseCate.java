package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dreamyoung.mprelation.*;
import lombok.AllArgsConstructor;
import top.dj.mapper.EquUseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * 设备投入使用方向实体类
 *
 * @author dj
 * @date 2021/3/9
 */

@AutoLazy
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("ems_equ_use_cate")
public class EquUseCate implements Serializable {
    private static final long serialVersionUID = -8090189867967980859L;

    private Integer id;             // 设备使用方向id
    private String useCate;         // 设备使用方向
    private String useCateDesc;     // 设备使用方向描述

    @TableField(exist = false)
    @ManyToMany(targetEntity = Equipment.class)
    @JoinTable(targetMapper = EquUseMapper.class, name = "ems_equ_use")
    @JoinColumn(name = "id", referencedColumnName = "cate_id")
    @JsonIgnore//json忽略投入此方向使用的设备
    @InverseJoinColumn(name = "id", referencedColumnName = "equ_id")
    private List<Equipment> equipments;//投入此使用方向的设备

    public EquUseCate() {
    }

    public EquUseCate(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EquUseCate{" +
                "id=" + id +
                ", useCate='" + useCate + '\'' +
                ", useCateDesc='" + useCateDesc + '\'' +
                ", equipments=" + equipments +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUseCate() {
        return useCate;
    }

    public void setUseCate(String useCate) {
        this.useCate = useCate;
    }

    public String getUseCateDesc() {
        return useCateDesc;
    }

    public void setUseCateDesc(String useCateDesc) {
        this.useCateDesc = useCateDesc;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
