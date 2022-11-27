package com.hongsheng.controller;

import com.alibaba.fastjson.JSON;
import com.hongsheng.dto.PersonInsertDto;
import com.hongsheng.dto.StudentDto;
import com.hongsheng.entity.Personal;
import com.hongsheng.service.IPersonalService;
import com.hongsheng.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("mysql")
@Slf4j
public class TestController {

    @Autowired
    private IPersonalService iPersonalService;

    @GetMapping("test")
    public String testQueryPersonal(){
        List<Personal> list = iPersonalService.list();
        Personal personal = list.get(0);
        return personal.toString();
    }

    @PostMapping("insert")
    public String testInsertPersonal(@RequestBody PersonInsertDto person){
        //BeanUtils.copyProperties(source,target);
        iPersonalService.insert(person);
        return "insert插入成功";
    }

    @PostMapping("insertSelective")
    public String testInsertSelectivePersonal(@RequestBody PersonInsertDto person){
        iPersonalService.insertSelective(person);
        return "insert selective插入成功";
    }

    /**
     * 测试JSON.toJSONString 一般会用这个方法将日志输出成标准的Json格式
     * 便于在浏览器中进行查看
     * @return
     */
    @GetMapping("stu")
    public String testStuJson(){
        StudentDto dto = new StudentDto();
        dto.setName("张三丰");
        dto.setSchool("北京大学");
        // log.info("dto:{}", dto);dto:StudentDto(name=张三丰, school=北京大学)
        // JSON.toJSONString dto:{"name":"张三丰","school":"北京大学"}
        log.info("dto:{}", JSON.toJSONString(dto));
        return dto.getName();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Personal> list = iPersonalService.getListByPersonal();
        ExcelUtil<Personal> util = new ExcelUtil<Personal>(Personal.class);
        util.exportExcel(response, list, "报表导出");
    }
}
