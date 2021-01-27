package top.dj.test;

/**
 * @author dj
 * @date 2021/1/12
 */
public class Test {
    public static void main(String[] args) {
        Son son1 = new Son(1, "test1", "blue");
        Son son2 = new Son(2, "test2", "blue");
        System.out.println(son1.equals(son2));
    }
}
