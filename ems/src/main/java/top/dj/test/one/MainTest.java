package top.dj.test.one;

import top.dj.POJO.DO.Role;

import java.util.List;

/**
 * @author dj
 * @date 2021/1/21
 */
public class MainTest {
    private final static Role role = new Role();
    static List<String> list;

    public static void main(String[] args) {
        System.out.println("(list == null) = " + (list == null));
        System.out.println(list);
        System.out.println("list.isEmpty() = " + list.isEmpty());

    }
}
