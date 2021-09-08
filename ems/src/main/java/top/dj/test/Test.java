package top.dj.test;

import com.alibaba.fastjson.JSONObject;
import top.dj.POJO.DO.User;

/**
 * @author dj
 * @date 2021/1/12
 */
public class Test {
    public static void main(String[] args) {
        User user = new User("doIt");
        JSONObject jsonObject = JSONObject.parseObject(user.toString());
        System.out.println("jsonObject = " + jsonObject);
    }
}
