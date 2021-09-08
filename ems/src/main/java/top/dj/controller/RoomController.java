package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.Room;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.RoomService;

/**
 * @author dj
 * @date 2021/1/13
 */
@RestController
@RequestMapping("/room")
public class RoomController extends BaseController<Room> {
    @Autowired
    private RoomService roomService;

    @GetMapping("/equNums")
    public ResultVO<int[]> getRoomEquNum() {
        int[] nums = roomService.getEquNum();
        return new ResultVO<>(20000, "实践室库存信息", nums);
    }
}
