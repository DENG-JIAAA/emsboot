package top.dj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.Room;

/**
 * @author dj
 * @date 2021/1/13
 */
@RestController
@RequestMapping("/room")
public class RoomController extends BaseController<Room> {
}
