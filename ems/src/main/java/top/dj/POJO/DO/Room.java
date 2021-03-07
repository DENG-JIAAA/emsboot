package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dj
 * @date 2021/1/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_room")
// 实践室实体类
public class Room {
    @TableId(type = IdType.AUTO)
    private Integer id;             // 实践室id
    private String roomName;        // 实践室名字
    private String roomDesc;        // 描述
}
