package top.dj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.DTO.BaseDto;
import top.dj.POJO.DO.BaseDO;
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
    public T findOneEntityByEntityId(@PathVariable("id") Integer id) {
        return baseService.findEntity(id);
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
    public List<T> findAllEntitiesInDB() {
        return baseService.findEntity();
    }

    /**
     * 添加一条 实体记录
     */
    @PostMapping("/add")
    public BaseDto<T> addOneEntity(@RequestBody T t) {
        return baseService.addOneEntity(t);
    }

    /**
     * 通过 id 修改一条实体记录
     */
    @PutMapping("/modify")
    public BaseDto<T> modifyOneEntity(@RequestBody T t) {
        return baseService.modifyOneEntity(t);
    }

    /**
     * 通过 id 删除一条实体记录
     */
    @DeleteMapping("/{id}")
    public Integer deleteOneEntity(@PathVariable("id") Integer id) {
        return baseService.deleteOneEntity(id);
    }
}
