package top.dj.service.impl;

import org.springframework.stereotype.Service;
import top.dj.POJO.DO.Room;
import top.dj.service.RoomService;

/**
 * @author dj
 * @date 2021/1/13
 */
@Service
public class RoomServiceImpl extends BaseServiceImpl<Room> implements RoomService {
    public RoomServiceImpl() {
        super(Room.class);
    }
}
