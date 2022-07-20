package com.hongsheng;

import com.hongsheng.entity.DistributeMethod;
import com.hongsheng.entity.DistributeStrategyDto;
import com.hongsheng.service.DistributeMethodService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class DistriTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DistributeMethodService distributeMethodService;

    @Test
    public void addDistributeTest(){
        DistributeStrategyDto distributeStrategyDto = new DistributeStrategyDto();
        distributeStrategyDto.setStoreId("27003");
        DistributeStrategyDto.Automatic automatic = new DistributeStrategyDto.Automatic();
        DistributeStrategyDto.Manual manual = new DistributeStrategyDto.Manual();
        automatic.setDistributePlatforms(Arrays.asList("1","2","3").stream().mapToInt(Integer::parseInt).toArray());
        automatic.setOrderPlatforms(Arrays.asList("10","20","30").stream().mapToInt(Integer::parseInt).toArray());

        manual.setDistributePlatforms(Arrays.asList("4","5","6").stream().mapToInt(Integer::parseInt).toArray());

        distributeStrategyDto.setType(2);
        distributeStrategyDto.setContent(automatic);

        Date date = new Date();
        distributeStrategyDto.setCreateTime(date);
        distributeStrategyDto.setUpdateTime(date);

        mongoTemplate.save(distributeStrategyDto);
    }

    @Test
    public void queryAllStrategyList(){
        List<DistributeStrategyDto> all = mongoTemplate.findAll(DistributeStrategyDto.class);
        for (DistributeStrategyDto dto : all){
            DistributeMethod distributeMethod = new DistributeMethod();
            distributeMethod.setMerchantId(dto.getStoreId());
            Integer type = dto.getType();
            if (1 == type){
                DistributeStrategyDto.Manual manual = (DistributeStrategyDto.Manual)dto.getContent();
                // Arrays.asList(manual.getDistributePlatforms())
                distributeMethod.setDistributePlatforms(arrToStr(manual.getDistributePlatforms()));
            }else {
                DistributeStrategyDto.Automatic automatic = (DistributeStrategyDto.Automatic)dto.getContent();
                distributeMethod.setOrderPlatforms(arrToStr(automatic.getOrderPlatforms()));
                distributeMethod.setDistributePlatforms(arrToStr(automatic.getDistributePlatforms()));
            }
            distributeMethod.setCreateTime(dto.getCreateTime());
            distributeMethod.setUpdateTime(dto.getUpdateTime());
            distributeMethodService.save(distributeMethod);
        }
    }

    private String arrToStr(int[] arr)
    {
        // 自定义一个字符缓冲区，
        StringBuilder sb = new StringBuilder();
        //遍历int数组，并将int数组中的元素转换成字符串储存到字符缓冲区中去
        for(int i=0; i<arr.length; i++)
        {
            if(i!=arr.length-1)
                sb.append(arr[i]+" ,");
            else
                sb.append(arr[i]);
        }
        return sb.toString();
    }
}
