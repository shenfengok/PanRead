/*
Navicat MySQL Data Transfer

Source Server         : root1
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : drupal08

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-11-25 11:01:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for queue_new
-- ----------------------------
DROP TABLE IF EXISTS `queue_new`;
CREATE TABLE `queue_new` (
  `item_id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key: Unique item ID.',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT 'The queue name.',
  `base_path` varchar(255) NOT NULL DEFAULT '' COMMENT 'The queue name.',
  `data` longblob DEFAULT NULL COMMENT 'The arbitrary data for the item.',
  `expire` int(11) NOT NULL DEFAULT 0 COMMENT 'Timestamp when the claim lease expires on the item.',
  `created` int(11) NOT NULL DEFAULT 0 COMMENT 'Timestamp when the item was created.',
  `todo` tinyint(1) DEFAULT NULL,
  `force` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `name_created` (`name`,`created`),
  KEY `expire` (`expire`)
) ENGINE=InnoDB AUTO_INCREMENT=1119213978109347 DEFAULT CHARSET=utf8mb4 COMMENT='Stores items in queues.';

-- ----------------------------
-- Records of queue_new
-- ----------------------------
INSERT INTO `queue_new` VALUES ('2567197234077', '08-深入拆解Java虚拟机', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('17976225100257', '04-左耳听风', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('21489049867468', '24-Java并发编程实战', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('27598889448510', '45-从0开始学游戏开发', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('30273335287282', '128-视觉笔记入门课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('40470094302261', '27-Android开发高手课', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('50160956307136', '117-后端存储实战课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('59265297881006', '25-软件测试52讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('102887340467989', '39-程序员进阶攻略', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('153904253858458', '35-面试现场', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('156208253445825', '156-动态规划面试宝典', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('157440401439744', '165-物联网开发实战', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('163664295207354', '101-后端技术面试38讲', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('173100507946229', '31-深入浅出计算机组成原理', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('185282258131952', '113-接口测试实战课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('188123048142133', '02-Java核心技术面试精讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('189642839258247', '139-跟月影学可视化', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('196670984574483', '32-Python核心技术与实战', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('219716381517249', '01-数据结构与算法之美', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('226482490102513', '09-Go语言核心36讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('229684218633741', '03-从0开始学架构', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('230935031759751', '49-赵成的运维体系管理课', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('236649184516446', '143-Vim 实用技巧必知必会', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('247626502330214', '129-系统性能调优必知必会', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('259127881910466', '43-软件工程之美', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('265789733322663', '137-正则表达式入门课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('275786362405491', '10-如何设计一个秒杀系统', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('283828550600385', '87-全栈工程师修炼指南', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('284091118251452', '162-乔新亮的CTO成长复盘', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('299934684079928', '06-MySQL实战45讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('300655922831157', '52-透视HTTP协议', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('316423861762223', '30-推荐系统三十六式', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('320764270608622', '122-SRE实战手册', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('324832329363742', '15-趣谈Linux操作系统', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('340183784365065', '145-重学线性代数', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('340284671035998', '159-Go 并发编程实战课', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('346275618842241', '121-图解 Google V8', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('351607690414206', '05-趣谈', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('357340960398221', '13-深入剖析Kubernetes', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('358164025219872', '41-10x程序员工作法', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('362690645058896', '136-编译原理实战课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('363795959997616', '149-Linux内核技术实战课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('390535478164697', '51-白话法律42讲', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('408324065293718', '26-人工智能基础课', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('409602706903658', '77-从0打造音视频直播系统', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('416203002069578', '48-从0开始做增长', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('437150682821215', '07-重学前端', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('454553781238242', '47-Java性能调优实战', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('459081746926346', '99-JavaScript核心原理解析', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('476330106989327', '88-高并发系统设计40问', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('490710026198350', '29-朱赟的技术管理课', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('515518179556868', '46-Kafka核心技术与实战', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('522828559742288', '34-技术与商业案例解读', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('523629932543972', '44-SQL必知必会', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('524148747141596', '123-检索技术核心20讲', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('548236384403719', '130-罗剑锋的C++实战笔记', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('573444380131948', '38-机器学习40讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('576527645406417', '160-人人都用得上的写作课', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('582405746258252', '104-性能工程高手课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('585650065038261', '119-Java 业务开发常见错误 100 例', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('588673085542099', '158-爱上跑步', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('605943376923287', '20-技术领导力300讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('612872410368332', '17-深入浅出区块链', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('615903140553863', '157-深度学习推荐系统实战', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('621195835058382', '97-项目管理实战20讲', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('626896109933850', '98-设计模式之美', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('635015970598106', '147-分布式数据库30讲', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('669999397316387', '114-分布式协议与算法实战', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('681173628453061', '105-安全攻防技能30讲', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('686983393201404', '11-程序员的数学基础课', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('698835351218485', '150-技术管理案例课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('726180354169294', '23-邱岳的产品实战', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('753982223670808', '18-技术管理实战36讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('764007294626889', '37-AI技术内参', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('768924225267402', '144-如何看懂一幅画', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('773737007105338', '53-OpenResty从入门到实战', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('781941951179116', '75-编辑训练营', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('784914242559411', '108-摄影入门课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('787964344536295', '80-网络编程实战', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('789557280998655', '86-即时消息技术剖析与实战', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('795802234194414', '16-从0开始学微服务', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('820739868254222', '19-数据分析实战45讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('823812106395779', '74-Flutter核心技术与实战', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('838008654367537', '21-从0开始学大数据', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('855137735533744', '94-DDD实战课', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('861073733380654', '90-分布式技术原理与算法解析', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('862346873236269', '141-OAuth 2.0实战课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('863635885586868', '36-大规模数据处理实战', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('868784738101290', '14-许式伟的架构课', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('871685579794828', '126-Kafka核心源码解读', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('876595205877896', '91-说透中台', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('877479874575179', '83-编译原理之美', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('898613456457502', '40-持续交付36讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('928104083811100', '106-性能测试实战30讲', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('930228242468283', '50-深入拆解Tomcat & Jetty', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('936433950782588', '95-苏杰的产品创新课', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('940770977340508', '110-说透敏捷', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('942534675788112', '124-数据中台实战课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('952316731239648', '92-DevOps实战笔记', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('954967934009220', '73-黄勇的OKR实战笔记', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('962926283985387', '154-用户体验设计实战课', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('963736685390951', '102-现代C++实战30讲', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('968336779497140', '12-Linux性能优化实战', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('971040645034861', '118-深入浅出云计算', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('976210791517984', '33-邱岳的产品手记', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('994224178551512', '109-人人都能学会的编程入门课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('998473067641081', '28-iOS开发高手课', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1001679897721974', '116-架构实战案例解析', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1004252009859251', '85-研发效率破局之道', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1009694361590758', '134-软件设计之美', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1009872364102685', '81-浏览器工作原理与实践', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1015719870973439', '131-互联网人的英语私教课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1022101157981678', '79-消息队列高手课', '00-资源文件/14-极客时间/01-专栏课/051-99/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1034853610404229', '148-To B市场品牌实战课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1043163351291812', '133-职场求生攻略', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1050459079191190', '155-WebAssembly入门课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1068644787528414', '161-手机摄影', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1069499803697394', '42 -代码精进之路', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1080130144710172', '22-硅谷产品实战36讲', '00-资源文件/14-极客时间/01-专栏课/01-50/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1086978157709278', '115-RPC实战与核心原理', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1094756506047680', '127-Serverless入门课', '00-资源文件/14-极客时间/01-专栏课/100-/', null, '0', '0', '1', null);
INSERT INTO `queue_new` VALUES ('1119213978109346', '146-Redis核心技术与实战', '00-资源文件/14-极客时间/00-更新中的专栏/', null, '0', '0', '1', null);
