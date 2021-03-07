package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dj
 * @date 2021/3/6
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_per_meta")
public class Meta {
    private Integer id;
    private String title;
    private String icon;

    /*@TableField("per_id")
    private Integer perId;
    @OneToOne
    @TableField(exist = false)
    @JoinColumn(name = "per_id", referencedColumnName = "id")
    private Permission permission;*/
}
