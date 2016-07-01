use sg;

CREATE TABLE `enford_market_research` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `start_dt` datetime DEFAULT NULL,
  `end_dt` datetime DEFAULT NULL,
  `exe_store_id` int(11) DEFAULT NULL,
  `res_store_id` int(11) DEFAULT NULL,
  `import_id` int(11) NOT NULL,
  `state` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `import_id_UNIQUE` (`import_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_market_research_commodity` (
  `id` int(11) DEFAULT NULL,
  `res_id` int(11) NOT NULL,
  `cod_id` int(11) NOT NULL,
  PRIMARY KEY (`res_id`,`cod_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `enford_market_research_dept` (
  `res_id` int(11) NOT NULL,
  `exe_id` int(11) NOT NULL,
  `comp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`res_id`,`exe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `parent` int(11) NOT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `img_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `province` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `art_no` varchar(45) DEFAULT NULL,
  `p_name` varchar(100) NOT NULL,
  `p_size` varchar(45) DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  `bar_code` varchar(45) DEFAULT NULL,
  `supplier_code` varchar(45) DEFAULT NULL,
  `category_code` int(11) DEFAULT '-1',
  `create_dt` datetime DEFAULT '0000-00-00 00:00:00',
  `update_dt` datetime DEFAULT '0000-00-00 00:00:00',
  `create_by` int(11) DEFAULT '-1',
  `update_by` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1301 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_competitors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `city_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `comp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_import_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `size` varchar(45) DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `file_type` varchar(10) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_price` float DEFAULT '0',
  `retail_price` float DEFAULT '0',
  `com_id` int(11) DEFAULT NULL,
  `miss` int(11) DEFAULT '0',
  `res_id` int(11) DEFAULT NULL,
  `comp_id` int(11) DEFAULT NULL,
  `upload_by` int(11) DEFAULT NULL,
  `upload_dt` datetime DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `prompt_price` float DEFAULT '0',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3917 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_product_supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) CHARACTER SET latin1 NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1298 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_system_menu` (
  `id` int(11) NOT NULL,
  `text` varchar(45) NOT NULL,
  `state` varchar(45) DEFAULT NULL,
  `parent` int(11) NOT NULL,
  `url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `enford_system_platform` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `org_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `enford_system_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_system_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `action` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

CREATE TABLE `enford_system_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `dept_id` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*v1.1新增数据表*/
CREATE TABLE `enford_custom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(45) DEFAULT NULL,
  `short_name` varchar(10) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='英福特客户表';

CREATE TABLE `enford_custom_product` (
  `custom_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `secret_key` varchar(32) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  PRIMARY KEY (`custom_id`,`product_id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='英福特客户开通产品表';

CREATE TABLE `enford_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='英福特产品表';

CREATE TABLE `enford_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `weixin_openid` varchar(45) DEFAULT NULL,
  `subscribe` int(1) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `city` varchar(15) DEFAULT NULL,
  `province` varchar(15) DEFAULT NULL,
  `language` varchar(45) DEFAULT NULL,
  `country` varchar(10) DEFAULT NULL,
  `headimgurl` varchar(100) DEFAULT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `unionid` varchar(45) DEFAULT NULL,
  `remark` varchar(45) DEFAULT NULL,
  `groupid` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `enford_product_category` (`id`,`code`,`name`,`parent`)VALUES(1,0,'根分类',-1);

/*
-- Query: SELECT * FROM test.enford_system_menu
LIMIT 0, 1000

-- Date: 2016-02-13 23:11
*/
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (0,'根目录','open',-1,NULL);
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (1,'市调','open',0,'');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (2,'基础数据导入','open',0,'');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (3,'基础数据管理','open',0,'');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (6,'管理员控制台','open',0,'');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (11,'进行中的市调清单','open',1,'/market_research/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (12,'已结束的市调清单','open',1,'/market_research/manage2');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (14,'市调清单导出','open',1,'/market_research/export2');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (21,'市调清单导入','open',2,'/import');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (22,'门店数据导入','open',2,'/import/dept');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (23,'市调人员导入','open',2,'/import/employee');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (24,'区域数据导入','open',2,'/import/area');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (31,'菜单管理','open',6,'/menu/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (32,'用户管理','open',6,'/user/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (33,'角色管理','open',6,'/role/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (34,'机构管理','open',6,'/org/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (311,'商品数据管理','open',3,'/cod/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (312,'品类数据管理','open',3,'/category/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (313,'供应商管理','open',3,'/supplier/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (314,'门店管理','open',3,'/dept/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (316,'竞争门店管理','open',3,'/competitor/manage');
INSERT INTO `enford_system_menu` (`id`,`text`,`state`,`parent`,`url`) VALUES (317,'商品价格管理','open',3,'/price/manage');

/*
-- Query: SELECT * FROM test.enford_system_role
LIMIT 0, 1000

-- Date: 2016-02-13 23:11
*/
INSERT INTO `enford_system_role` (`id`,`name`) VALUES (0,'超级管理员');
INSERT INTO `enford_system_role` (`id`,`name`) VALUES (1,'系统管理员');
INSERT INTO `enford_system_role` (`id`,`name`) VALUES (13,'管理员');

/*
-- Query: SELECT * FROM test.enford_system_role_menu
LIMIT 0, 1000

-- Date: 2016-02-13 23:12
*/
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (52,NULL,13,11,NULL);
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (53,NULL,13,12,NULL);
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (54,NULL,13,21,NULL);
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (55,NULL,13,22,NULL);
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (56,NULL,13,41,NULL);
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (57,NULL,13,31,NULL);
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (58,NULL,13,32,NULL);
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (59,NULL,13,33,NULL);
INSERT INTO `enford_system_role_menu` (`id`,`name`,`role_id`,`menu_id`,`action`) VALUES (60,NULL,13,34,NULL);

/*
-- Query: SELECT * FROM test.enford_system_user
LIMIT 0, 1000

-- Date: 2016-02-13 23:12
*/
INSERT INTO `enford_system_user` (`id`,`username`,`password`,`email`,`name`,`org_id`,`role_id`,`type`,`dept_id`) VALUES (1,'admin','21232f297a57a5a743894a0e4a801fc3',NULL,'管理员',1,0,0,-1);
INSERT INTO `enford_system_user` (`id`,`username`,`password`,`email`,`name`,`org_id`,`role_id`,`type`,`dept_id`) VALUES (6,'xiads','e10adc3949ba59abbe56e057f20f883e','247765564@qq.com','夏冬珊',1,13,0,-1);
INSERT INTO `enford_system_user` (`id`,`username`,`password`,`email`,`name`,`org_id`,`role_id`,`type`,`dept_id`) VALUES (7,'0323','098f6bcd4621d373cade4e832627b4f6','247765564@qq.com','测试员工',11,13,1,4);
INSERT INTO `enford_system_user` (`id`,`username`,`password`,`email`,`name`,`org_id`,`role_id`,`type`,`dept_id`) VALUES (8,'0324','098f6bcd4621d373cade4e832627b4f6','247765564@qq.com','测试员工2',11,13,1,191);
INSERT INTO `enford_system_user` (`id`,`username`,`password`,`email`,`name`,`org_id`,`role_id`,`type`,`dept_id`) VALUES (15,'888888','21218cca77804d2ba1922c33e0151105','888888@qq.com','通用',11,0,1,191);

/*
-- Query: SELECT * FROM test.enford_product_organization
LIMIT 0, 1000

-- Date: 2016-02-13 23:12
*/
INSERT INTO `enford_product_organization` (`id`,`name`,`address`,`full_name`) VALUES (1,'Enford','南京','Enford科技');
INSERT INTO `enford_product_organization` (`id`,`name`,`address`,`full_name`) VALUES (11,'苏果','南京','华润苏果');

/*v1.1数据*/
INSERT INTO `enford_product` (`id`, `name`) VALUES ('1', '商业情报分析系统');

INSERT INTO `enford_custom` (`id`,`full_name`,`short_name`,`address`) VALUES (1,'苏果超市有限公司','华润苏果','南京市秦淮区解放路55号');

INSERT INTO `enford_custom_product` (`custom_id`,`product_id`,`secret_key`,`start_date`,`end_date`,`code`) VALUES (1,1,'Amz6X7p1ZJdsW61Sxw0EAzjmcggpzQQK','2016-01-01 00:00:00','2018-12-31 00:00:00','SGMR');



