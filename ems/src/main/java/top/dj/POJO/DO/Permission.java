package top.dj.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dreamyoung.mprelation.*;
import org.springframework.security.core.GrantedAuthority;
import top.dj.mapper.RolePerMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author dj
 * @date 2021/1/13
 */
@AutoLazy
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("ems_permission")
// 权限实体类
public class Permission implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1301538782886939505L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;                 //权限id
    private String perName;             //权限英文名称
    private String perNameZh;           //权限中文名称
    private String perDesc;             //权限描述
    private String perMethod;           //请求资源的方法（GET/POST/PUT/DELETE...）
    private String perUrl;              //资源url（授权链接）
    private Integer pId;                //父节点id

    @TableField(exist = false)
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(targetMapper = RolePerMapper.class, name = "ems_role_per")
    @JoinColumn(name = "id", referencedColumnName = "per_id")
    @InverseJoinColumn(name = "id", referencedColumnName = "role_id")
    //@JsonIgnore
    private List<Role> perRoles;

    private String name;
    private String path;
    private String component;
    private String redirect;
    private Boolean alwaysShow;
    private Integer meta;

    public Permission() {
    }

    public Permission(Integer id) {
        this.id = id;
    }

    public Permission(Integer id, Integer pId) {
        this.id = id;
        this.pId = pId;
    }

    public Permission(String perName) {
        this.perName = perName;
    }

    public Permission(Integer id, String perName, String perNameZh, String perDesc, String perMethod, String perUrl, Integer pId, List<Role> perRoles, String name, String path, String component, String redirect, Boolean alwaysShow, Integer meta) {
        this.id = id;
        this.perName = perName;
        this.perNameZh = perNameZh;
        this.perDesc = perDesc;
        this.perMethod = perMethod;
        this.perUrl = perUrl;
        this.pId = pId;
        this.perRoles = perRoles;
        this.name = name;
        this.path = path;
        this.component = component;
        this.redirect = redirect;
        this.alwaysShow = alwaysShow;
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", perName='" + perName + '\'' +
                ", perNameZh='" + perNameZh + '\'' +
                ", perDesc='" + perDesc + '\'' +
                ", perMethod='" + perMethod + '\'' +
                ", perUrl='" + perUrl + '\'' +
                ", pId=" + pId +
                ", perRoles=" + perRoles +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", component='" + component + '\'' +
                ", redirect='" + redirect + '\'' +
                ", alwaysShow=" + alwaysShow +
                ", meta=" + meta +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerNameZh() {
        return perNameZh;
    }

    public void setPerNameZh(String perNameZh) {
        this.perNameZh = perNameZh;
    }

    public String getPerDesc() {
        return perDesc;
    }

    public void setPerDesc(String perDesc) {
        this.perDesc = perDesc;
    }

    public String getPerMethod() {
        return perMethod;
    }

    public void setPerMethod(String perMethod) {
        this.perMethod = perMethod;
    }

    public String getPerUrl() {
        return perUrl;
    }

    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public List<Role> getPerRoles() {
        return perRoles;
    }

    public void setPerRoles(List<Role> perRoles) {
        this.perRoles = perRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }

    @Override
    public String getAuthority() {
        return this.perName;
    }
}
