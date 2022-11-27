package com.hongsheng.test2022.test09;

/**
 * 泛型类的使用
 */
public interface TestFan {

    <T extends PersonBaseDTO> T getPersonBaseDTO(Class<T> tClass, PersonBaseDTO personBaseDTO) throws InstantiationException, IllegalAccessException;
}
