package com.hongsheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongsheng.dto.PersonInsertDto;
import com.hongsheng.entity.Personal;

import java.util.List;

public interface IPersonalService extends IService<Personal> {

    int insert(PersonInsertDto person);

    int insertSelective(PersonInsertDto person);

    List<Personal> getListByPersonal();
}
