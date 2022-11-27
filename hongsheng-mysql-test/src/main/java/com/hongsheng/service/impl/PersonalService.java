package com.hongsheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongsheng.dao.PersonalMapper;
import com.hongsheng.dto.PersonInsertDto;
import com.hongsheng.entity.Personal;
import com.hongsheng.service.IPersonalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PersonalService extends ServiceImpl<PersonalMapper, Personal>
        implements IPersonalService {
    @Autowired
    PersonalMapper personalMapper;

    public int insert(PersonInsertDto person){
        return personalMapper.add(person);
    }

    public int insertSelective(PersonInsertDto person){
        return personalMapper.addSelective(person);
    }

    public List<Personal> getListByPersonal(){
        QueryWrapper<Personal> queryWrapper= new QueryWrapper<>();
        List<Personal> personals = personalMapper.selectList(queryWrapper);
        return personals;
    }
}
