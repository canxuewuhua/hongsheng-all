# hongsheng-scorpio-elasticsearch项目笔记

> 本项目为日常学习es的案例代码


全文检索
是计算机程序通过扫描文章中的每一个词 对每一个词建立一个索引
指明该词在文章中出现的次数和位置
不同于百度、谷歌搜索 他们是带语义解析的搜索结果
es全文检索是不处理语义 只处理文本 
搜索时英文不区分大小写 结果列表有相关度排序
------------------------------------
全面、准确、快速是衡量全文检索系统的关键指标

什么是ElasticSearch 弹性检索
简称ES 是基于Apache Lucene构建的开源搜索引擎
#ES是基于java语言编写#
国内大厂几乎无一不用es

开启远程访问
network 0.0.0.0
:set nu 出现行号

es启动需要非root用户
------------------------------------------

Index
一个索引就是一个拥有几分相似特征的文档的集合
索引名字必须是小写字母

Mapping
映射 就是定义一个文档和它所包含的字段如何被存储和索引的过程

Document
文档
文档是索引中存储的一条条数据 一条文档是一个可被索引的最小单元

es服务
索引-表
映射-表结构
文档-数据行

添加文档
POST /products/_doc/1 手动指定id
POST /products/_doc/  自动生成id

文档查询
GET /products/_doc/1

文档删除
DELETE /products/_doc/1

更新文档 # 删除原始文档 再重新添加
所以原始内容需要传过去
PUT /products/_doc/1 "title":""iphone13"

# 更新文档 基于指定字段进行更新
POST /products/_doc/1/_update
{
  "doc":{
    "price":1.6
   }
}

# 文档批量操作 _bulk
POST /products/_doc/_bulk

# query DSL 语法
查询所有[match_all]
GET /products/_doc/_search
{ 
   "query":{
      "match_all":{}
    }
}
关键词查询[term]
GET /products/_mapping 看结构情况
# term 基于关键词查询 keyword 日后搜索使用 需全部内容搜索
# text 类型 默认 es 标准分词器 [中文单字分词] 英文单词分词
# keyword [不分词] 但是text类型分词
------------------------ keyword integer double date 都是不分词的 ----------
------------ [在es中 除了text类型分词其余类型不分词]-------
[在es中默认使用标准分词器 中文单字分词  英文 单词分词]
GET /products/_search
{
  "query":{
      "title":{
          "value":"小鱼豆腐"
       }
   }
}

# 范围查询 range
GET /products/_search
{
  "query":{
      "range":{
        "price":{
             "ge":10
             "le":20
         }
      }
   }
}

# 前缀查询 prefix
# 通配符查询 iphone*  ?匹配一个  * 匹配多个
# 多id查询ids

#fuzzy 模糊查询
# bool查询 
# 多字段查询 [multi_match]

# query_string

# 高亮查询 highlight

# 返回指定条数
# 分页查询 from size 还有排序

-------------IK分词器必须和es的版本保持一致------------
ext_dict
stop_dict

ElasticsearchOperations 主要是面向对象 站在java层面
RestHighLevelClient rest方式 更像kibana客户端
两个客户端可以去操作es

索引
映射
文档








