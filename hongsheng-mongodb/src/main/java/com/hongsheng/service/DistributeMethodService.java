package com.hongsheng.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongsheng.entity.DistributeMethod;
import com.hongsheng.mapper.DistributeMethodMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DistributeMethodService extends ServiceImpl<DistributeMethodMapper, DistributeMethod>
        implements IDistributeMethodService {
}
