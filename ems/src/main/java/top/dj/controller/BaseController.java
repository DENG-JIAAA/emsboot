package top.dj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dreamyoung.mprelation.AutoMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dj.POJO.VO.ResultVO;
import top.dj.service.EquipmentService;
import top.dj.service.MyIService;
import top.dj.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个实体应该具备的 基本增删查改
 *
 * @author dj
 * @date 2021/1/13
 */

@RestController
@Slf4j
public class BaseController<T> {
    @Autowired
    private MyIService<T> myIService;
    @Autowired
    private AutoMapper autoMapper;

    /**
     * 通过 实体id 获取一条数据
     *
     * @param id 实体 ID
     * @return 包装的实体 JSON
     */
    @GetMapping("/{id}")
    public ResultVO<T> findOneEntityByEntityId(@PathVariable("id") Integer id) {
        T t = myIService.getById(id);
        return new ResultVO<>
                (t != null ? 20000 : 20404, t != null ? "获取实体成功" : "实体[" + id + "]不存在", t);
    }

    /**
     * 通过 page、limit 获取实体分页数据
     *
     * @param page  当前页
     * @param limit 每页多少条数据
     * @return 分页 JSON 数据
     */
    @GetMapping("/{page}/{limit}")
    public IPage<T> findAllEntitiesByPage(@PathVariable("page") Integer page,
                                          @PathVariable("limit") Integer limit) {
        IPage<T> entityPage = myIService.page(new Page<>(page, limit));
        List<T> rawRecords = entityPage.getRecords();
        List<T> retRecords = new ArrayList<>();
        for (T record : rawRecords) {
            T one = myIService.getOne(new QueryWrapper<>(record));
            retRecords.add(one);
        }
        return entityPage.setRecords(retRecords);
    }

    /**
     * 获取当前实体所有的 数据库记录
     *
     * @return 实体所有 JSON 数据
     */
    @GetMapping("/all")
    @ApiOperation(value = "获取全部的实体对象")
    public ResultVO<List<T>> findAllEntitiesInDB() {
        List<T> entities = myIService.list();
        List<T> retList = new ArrayList<>();
        boolean OK = entities != null && !entities.isEmpty();
        if (OK) {
            for (T entity : entities) {
                T one = myIService.getOne(new QueryWrapper<>(entity));
                retList.add(one);
            }
        }
        return new ResultVO<>
                (OK ? 20000 : 20404, OK ? "获取实体列表成功" : "获取实体列表失败", retList);
    }

    /**
     * 添加一条 实体记录
     *
     * @param t 实体 body
     * @return 是否添加成功
     */
    @PostMapping("/add")
    public ResultVO<Boolean> addOneEntity(HttpServletRequest request, @RequestBody T t) {
        boolean save = myIService.save(t);
        return new ResultVO<>
                (save ? 20000 : 20404, save ? "添加实体成功" : "添加实体失败", save);
    }

    /**
     * 通过 id 修改一条实体记录
     *
     * @param t 实体 body
     * @return 是否修改成功
     */
    @PutMapping("/modify")
    public ResultVO<Boolean> modifyOneEntity(HttpServletRequest request, @RequestBody T t) {
        boolean update = myIService.updateById(t);
        return new ResultVO<>
                (update ? 20000 : 20404, update ? "修改实体成功" : "修改实体失败", update);
    }

    /**
     * 通过 id 删除一条实体记录
     *
     * @param id 实体 ID
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public ResultVO<Boolean> deleteOneEntity(HttpServletRequest request, @PathVariable("id") Integer id) {
        boolean remove = myIService.removeById(id);
        String defaultMsg = remove ? "删除实体成功" : "删除实体失败";
        String dynaMsg = "";
        if (myIService instanceof UserService) {
            dynaMsg = remove ? "删除用户成功" : "删除用户失败";
        } else if (myIService instanceof EquipmentService) {
            dynaMsg = remove ? "删除设备成功" : "删除设备失败";
        }
        return new ResultVO<>
                (remove ? 20000 : 20404, "".equals(dynaMsg) ? defaultMsg : dynaMsg, remove);
    }
}
