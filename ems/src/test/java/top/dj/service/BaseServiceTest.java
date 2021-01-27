package top.dj.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.dj.POJO.DO.Equipment;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class BaseServiceTest {
    @Autowired
    private BaseService<Equipment> baseService;

    @Test
    void testFindAll() {
        baseService.findEntity().forEach(System.out::println);
    }

    @Test
    void testAdd() {
        Random random = new Random();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        /*User user = new User();
        Equipment equipment = new Equipment(null, "ELE10011", "服务器", 3, "阿里巴巴", "E型服务器", "CoreX100/RX10P", "速度牌", "其他有关服务器的信息", 99999.99, 1, timestamp, timestamp, 100, 3, 1, 1, "轻易不可使用");
        System.out.println(baseService.addOneEntity(equipment));*/

        Equipment equ = new Equipment();
        for (int i = 105; i < 201; i++) {
            String str = UUID.randomUUID().toString();
            equ.setEquCode("code" + str.substring(0, 3) + i);
            equ.setEquName("name" + str.substring(0, 3) + i);
            equ.setEquCate(random.nextInt(3) + 1);
            equ.setEquFirm(str.substring(0, 3) + "公司" + (i + 1));
            equ.setEquModel(str.substring(0, 2) + "型设备");
            equ.setEquStandard(str.substring(0, 3) + "规格");
            equ.setEquBrand(str.substring(0, 3) + "品牌");
            equ.setEquOtherParam(str.substring(0, 6) + "其他参数");
            equ.setEquPrice(random.nextDouble());
            equ.setEquQuantity(random.nextInt(10) + 1);
            equ.setOutFirmTime(timestamp);
            equ.setPurchaseTime(timestamp);
            equ.setEquLife(random.nextInt(50) + 1);
            equ.setEquRoom(random.nextInt(5) + 1);
            equ.setEquUser(random.nextInt(200) + 1);
            equ.setEquState(random.nextInt(4) + 1);
            equ.setRemark(str);
            baseService.addOneEntity(equ);
        }
    }

}
