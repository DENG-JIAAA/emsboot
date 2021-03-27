package top.dj.test.one;

import java.text.ParseException;

/**
 * @author dj
 * @date 2021/1/21
 */
public class MainTest {
    public static void main(String[] args) throws ParseException {
        Integer integer = 10;
        Integer integer1 = null;
        System.out.println("integer.equals(integer1) = " + integer.equals(integer1));

        System.out.println("integer1.equals(integer) = " + integer1.equals(integer));

    }
}
