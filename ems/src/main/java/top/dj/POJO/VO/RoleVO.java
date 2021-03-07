package top.dj.POJO.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.dj.POJO.DO.Menu;

import java.util.List;

/**
 * @author dj
 * @date 2021/3/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVO {
    private String key;
    private String name;
    private String description;
    private List<Menu> routes;
}
