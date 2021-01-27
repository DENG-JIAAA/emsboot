package top.dj.POJO.VO;

import lombok.Data;

import java.util.List;

/**
 * @author dj
 * @date 2021/1/14
 */
@Data
public class DataVO<T> {
    private Integer code;
    private String msg;

    private Long current;
    private Long size;
    private Long total;
    private Long pages;
    private List<T> data;
}
