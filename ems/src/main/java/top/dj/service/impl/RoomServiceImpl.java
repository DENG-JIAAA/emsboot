package top.dj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.DO.Room;
import top.dj.mapper.EquipmentMapper;
import top.dj.mapper.RoomMapper;
import top.dj.service.RoomService;

import java.util.List;

/**
 * @author dj
 * @date 2021/1/13
 */
@Service
public class RoomServiceImpl extends MyServiceImpl<RoomMapper, Room> implements RoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public int[] getEquNum() {
        List<Equipment> equipmentList = equipmentMapper.selectList(null);
        List<Room> rooms = roomMapper.selectList(null);
        int[] nums = new int[rooms.size()];
        for (Equipment equipment : equipmentList) {
            int equRoomId = equipment.getEquRoom();
            for (Room room : rooms) {
                int roomId = room.getId();
                if (equRoomId == roomId) {
                    nums[roomId - 1]++;
                    break;
                }
            }
        }
        return nums;
    }
}
