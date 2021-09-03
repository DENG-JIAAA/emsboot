package top.dj.test;

/**
 * @author dj
 * @date 2021/1/12
 */
public class Test {
    public static void main(String[] args) {
        String url = "http://localhost:8080/equ/upload/avatar/1000";

        int i = url.lastIndexOf('/');
        String substring = url.substring(i);
        String s = url.substring(0, i);
        System.out.println("s = " + s);


    }
}
