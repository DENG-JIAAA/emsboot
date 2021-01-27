package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author dj
 * @date 2021/1/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("ems_equ_cate")
// 设备类别实体类
public class EquCate extends BaseDO{
    @TableId(type = IdType.AUTO)
    private Integer id; // 类别id
    private String cateName; // 类别名称
    private String cateDesc; // 类别描述
}
