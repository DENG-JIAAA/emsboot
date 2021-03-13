package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dj
 * @date 2021/1/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_equ_cate")
// 设备类别实体类
public class EquCate implements Serializable {
    private static final long serialVersionUID = -8360203209846722468L;
    @TableId(type = IdType.AUTO)
    private Integer id; // 设备类别id
    private String cateName; // 设备类别名称
    private String cateDesc; // 设备类别描述
}
