package top.dj.POJO.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author dj
 * @date 2021/1/20
 */
@Data
public class EquVO {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String equCode;
    private String equName;
    private String equCate;               // 设备类别
    private String equFirm;
    private String equModel;
    private String equStandard;
    private String equBrand;
    private String equOtherParam;
    private Double equPrice;
    private Integer equQuantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp outFirmTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp purchaseTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp storageTime;
    private Integer equLife;
    private String equRoom;                  // 存放的实践室
    private String equUser;                  // 负责人
    private String equState;                // 设备目前状态
    private String remark;
}
