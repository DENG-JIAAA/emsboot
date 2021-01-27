//package top.dj.service.impl;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import top.dj.POJO.DO.User;
//import top.dj.service.LoginService;
//
//@SpringBootTest
//class LoginServiceImplTest {
//    @Autowired
//    private LoginService loginService;
//    @Autowired
//    private LoginServiceImpl loginServiceImpl;
//
//    @Test
//    void testFindOneByNameAndPwd() {
//        LoginService loginService = new LoginServiceImpl();
//        User user = new User();
//        user.setLoginName("aDmIn");
//        user.setLoginPwd("123456");
//        System.out.println(loginServiceImpl.getOneByNameAndPwd(user));
//        User user2 = new User();
//        user2.setLoginName("9af93b6");
//        user2.setLoginPwd("pwd9aF");
//        System.out.println(loginServiceImpl.getOneByNameAndPwd(user2));
//
//    }
//
//}
