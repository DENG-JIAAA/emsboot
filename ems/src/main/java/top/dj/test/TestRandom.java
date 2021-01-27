package top.dj.test;

import java.util.Random;

/**
 * @author dj
 * @date 2021/1/12
 */
public class TestRandom {
    public static void main(String[] args) {
        /*Random random = new Random();
        for (int i = 0; i < 20; i++) {
            System.out.println(random.nextInt(3) + 1);
        }*/

        /*String str = UUID.randomUUID().toString();
        System.out.println(str);
        System.out.println(str.substring(0, 5));*/

        /*System.out.println("asdads".toUpperCase());*/

        for (int i = 0; i < 100; i++) {
            System.out.println((new Random().nextInt(9) + 1) * 10000000000L + i + "");
        }
    }
}
