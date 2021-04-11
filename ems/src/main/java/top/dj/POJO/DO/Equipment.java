package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dreamyoung.mprelation.*;
import lombok.AllArgsConstructor;
import top.dj.mapper.EquApprovalMapper;
import top.dj.mapper.EquUseMapper;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 设备实体类
 *
 * @author dj
 * @date 2021/1/12
 */
@AutoLazy
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("ems_equipment")
public class Equipment implements Serializable {
    private static final long serialVersionUID = -4603944842538580585L;

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
    private Integer useCount;               //设备使用次数
    private String equPicture;             //设备图片
    private String remark;                  //备注

    @TableField(exist = false)
    @ManyToMany(targetEntity = EquApprovalStatus.class)
    @JoinTable(targetMapper = EquApprovalMapper.class, name = "ems_equ_approval")
    @JoinColumn(name = "id", referencedColumnName = "equ_id")
    @InverseJoinColumn(name = "id", referencedColumnName = "approval_status_id")
    private List<EquApprovalStatus> statuses;//设备目前的审批状态

    @TableField(exist = false)
    @ManyToMany(targetEntity = EquUseCate.class)
    @JoinTable(targetMapper = EquUseMapper.class, name = "ems_equ_use")
    @JoinColumn(name = "id", referencedColumnName = "equ_id")
    @InverseJoinColumn(name = "id", referencedColumnName = "cate_id")
    private List<EquUseCate> useCates;      //设备投入使用的方向


    public Equipment() {
    }

    public Equipment(Integer id) {
        this.id = id;
    }

    public Equipment(Integer id, Integer equRoom) {
        this.id = id;
        this.equRoom = equRoom;
    }

    public Equipment(String equName) {
        this.equName = equName;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", equCode='" + equCode + '\'' +
                ", equName='" + equName + '\'' +
                ", equCate=" + equCate +
                ", equFirm='" + equFirm + '\'' +
                ", equModel='" + equModel + '\'' +
                ", equStandard='" + equStandard + '\'' +
                ", equBrand='" + equBrand + '\'' +
                ", equOtherParam='" + equOtherParam + '\'' +
                ", equPrice=" + equPrice +
                ", equQuantity=" + equQuantity +
                ", outFirmTime=" + outFirmTime +
                ", purchaseTime=" + purchaseTime +
                ", storageTime=" + storageTime +
                ", equLife=" + equLife +
                ", equRoom=" + equRoom +
                ", equUser=" + equUser +
                ", equState=" + equState +
                ", useCount=" + useCount +
                ", equPicture='" + equPicture + '\'' +
                ", remark='" + remark + '\'' +
                ", statuses=" + statuses +
                ", useCates=" + useCates +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquCode() {
        return equCode;
    }

    public void setEquCode(String equCode) {
        this.equCode = equCode;
    }

    public String getEquName() {
        return equName;
    }

    public void setEquName(String equName) {
        this.equName = equName;
    }

    public Integer getEquCate() {
        return equCate;
    }

    public void setEquCate(Integer equCate) {
        this.equCate = equCate;
    }

    public String getEquFirm() {
        return equFirm;
    }

    public void setEquFirm(String equFirm) {
        this.equFirm = equFirm;
    }

    public String getEquModel() {
        return equModel;
    }

    public void setEquModel(String equModel) {
        this.equModel = equModel;
    }

    public String getEquStandard() {
        return equStandard;
    }

    public void setEquStandard(String equStandard) {
        this.equStandard = equStandard;
    }

    public String getEquBrand() {
        return equBrand;
    }

    public void setEquBrand(String equBrand) {
        this.equBrand = equBrand;
    }

    public String getEquOtherParam() {
        return equOtherParam;
    }

    public void setEquOtherParam(String equOtherParam) {
        this.equOtherParam = equOtherParam;
    }

    public Double getEquPrice() {
        return equPrice;
    }

    public void setEquPrice(Double equPrice) {
        this.equPrice = equPrice;
    }

    public Integer getEquQuantity() {
        return equQuantity;
    }

    public void setEquQuantity(Integer equQuantity) {
        this.equQuantity = equQuantity;
    }

    public Timestamp getOutFirmTime() {
        return outFirmTime;
    }

    public void setOutFirmTime(Timestamp outFirmTime) {
        this.outFirmTime = outFirmTime;
    }

    public Timestamp getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Timestamp getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Timestamp storageTime) {
        this.storageTime = storageTime;
    }

    public Integer getEquLife() {
        return equLife;
    }

    public void setEquLife(Integer equLife) {
        this.equLife = equLife;
    }

    public Integer getEquRoom() {
        return equRoom;
    }

    public void setEquRoom(Integer equRoom) {
        this.equRoom = equRoom;
    }

    public Integer getEquUser() {
        return equUser;
    }

    public void setEquUser(Integer equUser) {
        this.equUser = equUser;
    }

    public Integer getEquState() {
        return equState;
    }

    public void setEquState(Integer equState) {
        this.equState = equState;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public String getEquPicture() {
        return equPicture;
    }

    public void setEquPicture(String equPicture) {
        this.equPicture = equPicture;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<EquUseCate> getUseCates() {
        return useCates;
    }

    public void setUseCates(List<EquUseCate> useCates) {
        this.useCates = useCates;
    }

    public List<EquApprovalStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<EquApprovalStatus> statuses) {
        this.statuses = statuses;
    }
}
