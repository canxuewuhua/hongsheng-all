package com.hongsheng.test2022.test09;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestFanController {

    @Autowired
    private TestResultService resultService;

    @GetMapping("fan")
    public String getFanStr() throws InstantiationException, IllegalAccessException {
        return JSON.toJSONString(resultService.testResult());
    }
}
