package top.dj.service.impl;

import org.springframework.stereotype.Service;
import top.dj.POJO.DO.EquCate;
import top.dj.service.EquCateService;

/**
 * @author dj
 * @date 2021/1/20
 */
@Service
public class EquCateServiceImpl extends BaseServiceImpl<EquCate> implements EquCateService {
    public EquCateServiceImpl() {
        super(EquCate.class);
    }
}
