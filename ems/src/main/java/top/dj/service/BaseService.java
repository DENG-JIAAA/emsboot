package top.dj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.dj.POJO.DTO.BaseDto;
import top.dj.POJO.DO.BaseDO;

import java.util.List;

public interface BaseService<T extends BaseDO> {
    default T findEntity(Integer id) {
        return null;
    }

    default IPage<T> findEntity(Integer page, Integer limit) {
        return null;
    }

    default BaseDto<T> addOneEntity(T t) {
        return null;
    }

    default BaseDto<T> modifyOneEntity(T t) {
        return null;
    }

    default Integer deleteOneEntity(Integer id) {
        return null;
    }

    default List<T> findEntity(){
        return null;
    }
}
