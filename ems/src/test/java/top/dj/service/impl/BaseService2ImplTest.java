//package top.dj.service.impl;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import top.dj.pojo.DO.Room;
//import top.dj.service.RoomService;
//
//@SpringBootTest
//class BaseService2ImplTest {
//    @Autowired
//    private RoomService roomService;
//
//    @Test
//    void testAdd() {
//        Room room = new Room(null, "实践室D", null);
//        System.out.println(roomService.addOneEntity(room));
//    }
//
//    @Test
//    void testModify() {
//        Room room = new Room(4, "实践室DD", "一些描述信息");
//        System.out.println(roomService.modifyOneEntity(room));
//    }
//
//    @Test
//    void testDelete() {
//        System.out.println(roomService.deleteOneEntity(4));
//    }
//
//}
