package top.dj.POJO.DO;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author DengJia
 * @Date 2021/5/3
 * @Description: 设备库计分策略
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ems_strategy")
public class ScoreStrategy implements Serializable {
    private static final long serialVersionUID = -6140885853790174463L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roomId;//计分策略属于哪个实践室
    private Boolean isNums;//借用次数纳入计算
    private Boolean isQuantity;//借用数量纳入计算
    private Boolean isUseCate;//使用方向纳入计算


    private Integer numsWeight;
    private Integer quantityWeight;

    /* 各项权重 */
    private Integer keYanWeight;
    private Integer biSaiWeight;
    private Integer shouKeWeight;
    private Integer geRenWeight;
    private Integer xiaoWaiWeight;
    private Integer qiTaWeight;

    /* 是否展示 */
    private Boolean showKeYan;
    private Boolean showBiSai;
    private Boolean showShouKe;
    private Boolean showGeRen;
    private Boolean showXiaoWai;
    private Boolean showQiTa;

    public ScoreStrategy(Integer id, Integer roomId) {
        this.id = id;
        this.roomId = roomId;
    }
}
