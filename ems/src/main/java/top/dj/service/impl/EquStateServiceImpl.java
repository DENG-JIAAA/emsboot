package top.dj.service.impl;

import org.springframework.stereotype.Service;
import top.dj.POJO.DO.EquState;
import top.dj.service.EquStateService;

/**
 * @author dj
 * @date 2021/1/21
 */
@Service
public class EquStateServiceImpl extends BaseServiceImpl<EquState> implements EquStateService {
    public EquStateServiceImpl() {
        super(EquState.class);
    }
}

