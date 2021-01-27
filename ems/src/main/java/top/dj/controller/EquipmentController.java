package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.Equipment;
import top.dj.POJO.VO.DataVO;
import top.dj.POJO.VO.EquVO;
import top.dj.service.EquipmentService;

/**
 * @author dj
 * @date 2021/1/12
 */
@RestController
@RequestMapping("/equ")
public class EquipmentController extends BaseController<Equipment> {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/vo/{id}")
    public EquVO findEquVO(@PathVariable("id") Integer id) {
        return equipmentService.findEquVO(id);
    }

    @GetMapping("/vo/{page}/{limit}")
    public DataVO<EquVO> findEquVO(@PathVariable("page") Integer page,
                                   @PathVariable("limit") Integer limit) {
        return equipmentService.findEquVO(page, limit);
    }

}
