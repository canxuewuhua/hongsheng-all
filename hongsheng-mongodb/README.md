# hongsheng-mongodb项目笔记

> 本项目为日常学习mongodb的案例代码，主要是一些语法要熟悉练习


 库 DataBase
集合 Collection
文档 Document
 
# 键值对
文档不需要设置相同的字段 并且相同的字段不需要相同的数据类型 这与关系型数据库有很大的区别 也是非常突出的特点
文档操作

insert
每个文档都会有一个_id作为唯一标识 _id默认会自动生成如果手动指定将使用手动指定的值作为_id的值
db.users.remove({_id:'dafdsfd'});
db.users.remove({_id:1});
db.users.find();
db.users.remove({});  # 删除所有
db.users.find().pretty();
query查看
db.集合.find();
AND OR 
like模糊查询
排序 类似sql的order by name,age
分页 类似sql语句 limit start,rows   在 mongo中是   skip(2).limit(4);
去重 distinct()
指定返回值
db.users.find({},{age:0})  # 即查询结果排除age
db.users.find({},{age:0,name:1})  # 这种会报错，name等于1意味着返回，不会指定


$type 是基于BSON类型来检索集合中匹配的数据类型 并返回结果

mongodb 可以创建索引 以提高查询速度
db.users.createIndex({name:1})
查看有哪些索引
db.users.getIndexes();
expireAfterSeconds 指定时间后索引失效

复合索引和关系型数据库一致 都是最左前缀匹配

聚合查询 count(*)
db.集合名称.aggregate()
分组 $group $num
sum avg max min

mongodb的索引使用的是B+树的数据结构
https://www.modb.pro/db/332028

文档操作
save方法和insert方法区别
save在主键重复的时候是更新 但insert是报错
insert可以批处理 mongoTemplate.insert(users,User.class); 需要指定实体类
save只能一条一条的插入
批量建议使用insert方法

副本集
主节点负责读写 从节点提供故障转移、主从复制 数据备份 高可用 不负责分担主节点压力
分片集群
解决单点压力问题 也叫分区 解决单节点物理存储上限问题
使用hash进行分片 使分布更均匀