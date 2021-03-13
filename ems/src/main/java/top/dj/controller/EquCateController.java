package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dj.POJO.DO.EquCate;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.EquCateService;

import java.util.Map;

/**
 * @author dj
 * @date 2021/1/20
 */
@RestController
@RequestMapping("/equCate")
public class EquCateController extends BaseController<EquCate> {
    @Autowired
    private EquCateService equCateService;

    @GetMapping("/map")
    public ResultVO<Map<Integer, Integer>> getTheMapOfCate() {
        Map<Integer, Integer> map = equCateService.getTheMapOfCate();
        boolean OK = map != null && !map.isEmpty();
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取设备类别成功" : "获取设备类别失败", map);
    }

    @GetMapping("/mapWithName")
    public ResultVO<Map<Integer, String>> getTheMapWithNameOfCate() {
        Map<Integer, String> map = equCateService.getTheMapWithNameOfCate();
        boolean OK = map != null && !map.isEmpty();
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取设备类别成功" : "获取设备类别失败", map);
    }

}
