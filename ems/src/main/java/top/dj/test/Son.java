package top.dj.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dj
 * @date 2021/1/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Son extends Father{
    private String color;

    public Son(int id, String name, String color) {
        super(id, name);
        this.color = color;
    }
}
