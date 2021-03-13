package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dreamyoung.mprelation.AutoLazy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/3/9
 */

@Data
@AutoLazy
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_equ_use")
public class EquUse implements Serializable {
    private static final long serialVersionUID = 3108856537816152720L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("equ_id")
    private Integer equId;//设备id

    @TableField("cate_id")
    private Integer cateId;//使用方向id

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp equUseTime;//使用时间

    @TableField("remark")
    private String remark;//备注

}
