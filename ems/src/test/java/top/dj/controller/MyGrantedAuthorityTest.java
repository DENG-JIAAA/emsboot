package top.dj.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.component.MyGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class MyGrantedAuthorityTest {

    @Test
    void test01() {
        MyGrantedAuthority authority1 = new MyGrantedAuthority("/index", "GET");
        MyGrantedAuthority authority2 = new MyGrantedAuthority("/index", "GET");

        System.out.println("authority1.equals(authority2) = " + authority1.equals(authority2));

        Set<MyGrantedAuthority> hashSet = new HashSet<>();
        hashSet.add(authority1);
        hashSet.add(authority2);

        for (MyGrantedAuthority myGrantedAuthority : hashSet) {
            System.out.println("myGrantedAuthority = " + myGrantedAuthority);
        }
    }

}
