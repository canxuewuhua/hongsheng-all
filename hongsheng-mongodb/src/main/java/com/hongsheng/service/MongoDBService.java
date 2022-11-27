package com.hongsheng.service;

import com.hongsheng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MongoDBService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 文档 查询操作
     */
    public void query(){
//        User user = new User();
//        user.setName("张三丰");
//        user.setSalary(5000d);
//        user.setBirthday(new Date());
//        mongoTemplate.insert(user);
//        List<User> all = mongoTemplate.findAll(User.class,"user");
//        List<User> users = mongoTemplate.find(new Query(), User.class);// 查询条件为空

        Query query = Query.query(Criteria.where("name").is("张三丰"));//此处使用name和username都可以
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println(users);
        System.out.println("-------------");
        // 大于 小于
        List<User> salary = mongoTemplate.find(Query.query(Criteria.where("salary").lt(2000d)), User.class);
        System.out.println(salary);
        salary.forEach(System.out::println);
        System.out.println("-------------");

        // or
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("name").is("张三丰"),
                Criteria.where("salary").gt(2200d)
        );
        List<User> users1 = mongoTemplate.find(Query.query(criteria), User.class);
        users1.forEach(System.out::println);
        System.out.println("-------------");

        // and 和 or同时存在
        // 分页查询
        // 总条数
        // 去重
        // 使用json 字符串方式查询
        Query query1 = new BasicQuery("{name:'张三丰'}");
        List<User> users2 = mongoTemplate.find(query1, User.class);
        users2.forEach(System.out::println);
        System.out.println("-------------");

        // 更新 删除操作
        Update update = new Update();
        update.set("salary", 8000d);
        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("张三丰")), update, User.class);
        System.out.println("更新成功");

    }
}
