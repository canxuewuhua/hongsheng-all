package com.hongsheng.test2022.test09;

import org.springframework.stereotype.Service;

@Service
public class TestStuStudyImpl implements TestFan{

    @Override
    public <T extends PersonBaseDTO> T getPersonBaseDTO(Class<T> tClass, PersonBaseDTO personBaseDTO) throws InstantiationException, IllegalAccessException {
        T personBaseDto = tClass.newInstance();
        personBaseDto.setId(1001L);
        personBaseDto.setName("张三丰");
        return personBaseDto;
    }
}
