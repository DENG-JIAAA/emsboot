package top.dj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import top.dj.POJO.DTO.BaseDto;
import top.dj.POJO.DO.BaseDO;
import top.dj.service.BaseService;

import java.util.List;

/**
 * @author dj
 * @date 2021/1/13
 */
@Service
@Primary
public class BaseServiceImpl<T extends BaseDO> implements BaseService<T> {
    @Autowired
    private BaseMapper<T> baseMapper;
    private Wrapper<T> wrapper;
    private T tObj;

    public BaseServiceImpl() {
    }

    public BaseServiceImpl(Class<T> c) {
        try {
            tObj = c.newInstance(); // 利用反射创建实例
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T findEntity(Integer id) {
        tObj.setId(id);
        wrapper = new QueryWrapper<>(tObj);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public IPage<T> findEntity(Integer page, Integer limit) {
        return baseMapper.selectPage(new Page<>(page, limit), null);
    }

    @Override
    public BaseDto<T> addOneEntity(T t) {
        int count = baseMapper.insert(t);
        t.setId(t.getId());
        return new BaseDto<T>(count, t);
    }

    @Override
    public BaseDto<T> modifyOneEntity(T t) {
        int count = baseMapper.updateById(t);
        // 1-修改成功 0-修改失败
        return new BaseDto<>(count == 1 ? 1 : 0, t);
    }

    @Override
    public Integer deleteOneEntity(Integer id) {
        tObj.setId(id);
        wrapper = new QueryWrapper<>(tObj);
        return baseMapper.delete(wrapper);
    }

    @Override
    public List<T> findEntity() {
        return baseMapper.selectList(null);
    }
}
