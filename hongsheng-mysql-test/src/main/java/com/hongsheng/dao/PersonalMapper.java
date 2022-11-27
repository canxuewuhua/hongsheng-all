package com.hongsheng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongsheng.dto.PersonInsertDto;
import com.hongsheng.entity.Personal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalMapper extends BaseMapper<Personal> {

    /**
     * 不需要返回主键
     * 返回自增主键@Options(useGeneratedKeys = true, keyProperty = "id")
     * 返回非自增主键@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = Long.class, before = false)
     *
     * 如果数据库中字段定义的时候，非空字段不给默认值的话
     * 插入的时候必须指定，如果没有则报错
     * 给了默认值，插入的时候不插入该字段，数据库会给指定默认值
     */
    @Insert({"insert into personal(name, addr, country, province_code) values(#{name}, #{address}, #{country}, #{provinceCode})"})
    int add(PersonInsertDto person);

    @Insert({
            "<script>",
                "insert into personal",
                "<trim prefix='(' suffix=')' suffixOverrides=','>",
                    "<if test='name != null'>",
                    "name,",
                    "</if>",
                    "<if test='address != null'>",
                    "addr,",
                    "</if>",
                    "<if test='country != null'>",
                    "country,",
                    "</if>",
                    "<if test='provinceCode != null'>",
                    "province_code,",
                    "</if>",
                "</trim>",
                "<trim prefix='values (' suffix=')' suffixOverrides=','>",
                    "<if test='name != null'>",
                    "#{name,jdbcType=VARCHAR},",
                    "</if>",
                    "<if test='address != null'>",
                    "#{address,jdbcType=VARCHAR},",
                    "</if>",
                    "<if test='country != null'>",
                    "#{country,jdbcType=VARCHAR},",
                    "</if>",
                    "<if test='provinceCode != null'>",
                    "#{provinceCode,jdbcType=VARCHAR},",
                    "</if>",
                "</trim>",
            "</script>"
    })
    int addSelective(PersonInsertDto person);
}
