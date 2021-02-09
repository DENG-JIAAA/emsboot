package top.dj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DTO.BaseDto;
import top.dj.POJO.DO.BaseDO;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.BaseService;

import java.util.List;

/**
 * @author dj
 * @date 2021/1/13
 */
@RestController // 一个实体应该具备的 基本增删查改
public class BaseController<T extends BaseDO> {
    @Autowired
    private BaseService<T> baseService;

    /**
     * 通过 实体id 获取一条数据
     */
    @GetMapping("/{id}")
    public ResultVO<T> findOneEntityByEntityId(@PathVariable("id") Integer id) {
        T entity = baseService.findEntity(id);
        return new ResultVO<>(20000, "获取实体", entity);
    }

    /**
     * 通过 page、limit 获取实体分页数据
     */
    @GetMapping("/{page}/{limit}")
    public IPage<T> findAllEntitiesByPage(@PathVariable("page") Integer page,
                                          @PathVariable("limit") Integer limit) {
        return baseService.findEntity(page, limit);
    }

    /**
     * 获取当前实体所有的 数据库记录
     */
    @GetMapping("/")
    public ResultVO<List<T>> findAllEntitiesInDB() {
        List<T> list = baseService.findEntity();
        return new ResultVO<>(20000, "获取实体列表", list);
    }

    /**
     * 添加一条 实体记录
     */
    @PostMapping("/add")
    public ResultVO<BaseDto<T>> addOneEntity(@RequestBody T t) {
        BaseDto<T> baseDto = baseService.addOneEntity(t);
        return new ResultVO<>(20000, "添加实体", baseDto);
    }

    /**
     * 通过 id 修改一条实体记录
     */
    @PutMapping("/modify")
    public ResultVO<BaseDto<T>> modifyOneEntity(@RequestBody T t) {
        BaseDto<T> baseDto = baseService.modifyOneEntity(t);
        return new ResultVO<>(20000, "修改实体", baseDto);
    }

    /**
     * 通过 id 删除一条实体记录
     */
    @DeleteMapping("/{id}")
    public ResultVO<Integer> deleteOneEntity(@PathVariable("id") Integer id) {
        Integer integer = baseService.deleteOneEntity(id);
        return new ResultVO<>(20000, "删除实体", integer);
    }
}
