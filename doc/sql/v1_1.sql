--创建区域表
CREATE TABLE `enford_product_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

--修改部门表
ALTER TABLE `test`.`enford_product_department` 
ADD COLUMN `area_id` INT NULL AFTER `comp_id`;

--设置部门的默认区域为-1
ALTER TABLE `test`.`enford_product_department`
CHANGE COLUMN `area_id` `area_id` INT(11) NULL DEFAULT -1;

--将现有的部门区域id刷为-1
set SQL_SAFE_UPDATES=0;
update enford_product_department set area_id=-1 where area_id is null;
