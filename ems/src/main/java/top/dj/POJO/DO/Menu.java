package top.dj.POJO.DO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dj
 * @date 2021/3/2
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable {
    private Integer id;
    private Integer pId;
    private String name;
    private String path;
    private String component;
    private String redirect;
    private Boolean alwaysShow;

    private Meta meta;
    private List<Menu> children;
}
