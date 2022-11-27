package com.hongsheng.test2022.test09;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestResultService {

    @Autowired
    private TestFan testFan;

    /**
     * 用泛型类的好处是 可以扩展基类的属性
     * 该例子只是说明 泛型可以这样使用，其实只要继承了父类 就拥有了父类的属性了
     * 如果父类需要复杂的业务逻辑处理了 可以使用泛型类进行处理
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public StudentStudyDto testResult() throws InstantiationException, IllegalAccessException {
        PersonBaseDTO personBaseDTO = new PersonBaseDTO();
        StudentStudyDto studentStudyDto = testFan.getPersonBaseDTO(StudentStudyDto.class, personBaseDTO);
        studentStudyDto.setStuName("一年级");
        return studentStudyDto;
    }
}
