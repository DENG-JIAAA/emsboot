package top.dj.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dj.POJO.DO.BaseDO;

/**
 * @author dj
 * @date 2021/1/21
 */

//@Component
public class Convert<T extends BaseDO> {
    private T tObj;

    public Convert(Class<T> c) {
        try {
            tObj = c.newInstance(); // 利用反射创建实例
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertDOToVO(int id, BaseMapper<Object>... mappers) {
        tObj.setId(id);
        Object entity = null;
        for (int i = 0; i < mappers.length; i++) {
            BaseMapper<Object> entityMapper = mappers[0];
            entity = entityMapper.selectOne(new QueryWrapper<>(tObj));
        }
        if (entity instanceof BaseDO) {
            tObj = (T) entity;
        }
    }
}
