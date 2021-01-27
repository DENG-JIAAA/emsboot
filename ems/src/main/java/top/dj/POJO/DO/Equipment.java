package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/1/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("ems_equipment")
// 设备实体类
public class Equipment extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Integer id;                     //设备id，自增主键
    private String equCode;                 //设备编号，唯一索引
    private String equName;                 //设备名称
    private Integer equCate;                //设备类别id
    private String equFirm;                 //设备生产厂商
    private String equModel;                //设备型号
    private String equStandard;             //设备规格
    private String equBrand;                //设备品牌
    private String equOtherParam;           //设备其他信息（主要电气及性能参数）
    private Double equPrice;                //设备单价
    private Integer equQuantity;            //设备数量
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp outFirmTime;          //出厂日期
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp purchaseTime;         //购置日期
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp storageTime;          //入库日期
    private Integer equLife;                //使用年限
    private Integer equRoom;                //存放的实践室id
    private Integer equUser;                //负责人id
    private Integer equState;               //设备目前状态id
    private String remark;                  //备注

}
