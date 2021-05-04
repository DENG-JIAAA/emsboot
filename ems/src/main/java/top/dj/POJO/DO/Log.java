package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/4/27
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_log")
public class Log implements Serializable {
    private static final long serialVersionUID = -5583292254172901409L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String operate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp operateTime;
    private String operater;//操作员
    private String operand;//操作对象

    public Integer getId() {
        return id;
    }

    public Log setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOperate() {
        return operate;
    }

    public Log setOperate(String operate) {
        this.operate = operate;
        return this;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public Log setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
        return this;
    }

    public String getOperater() {
        return operater;
    }

    public Log setOperater(String operater) {
        this.operater = operater;
        return this;
    }

    public String getOperand() {
        return operand;
    }

    public Log setOperand(String operand) {
        this.operand = operand;
        return this;
    }
}
