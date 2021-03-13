package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dj
 * @date 2021/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_equ_state")
public class EquState implements Serializable {
    private static final long serialVersionUID = 8810848421740028480L;
    private Integer id; // 设备状态id
    private String equState; // 状态名称
    private String remark; // 状态描述
}
