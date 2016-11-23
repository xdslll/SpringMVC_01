CREATE TABLE `enford_market_location` (
  `res_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='市调签到表';

ALTER TABLE `enford_market_research`
ADD COLUMN `bill_number` VARCHAR(12) NULL AFTER `state`;

ALTER TABLE `enford_market_research`
ADD COLUMN `type` VARCHAR(4) NULL AFTER `bill_number`;

ALTER TABLE `enford_market_research`
ADD COLUMN `unit_area` INT(11) NULL AFTER `type`;

ALTER TABLE `enford_market_research`
ADD COLUMN `frequency` INT(11) NULL AFTER `unit_area`;

ALTER TABLE `enford_market_research`
ADD COLUMN `frequency_days` INT(11) NULL AFTER `frequency`;

ALTER TABLE `enford_market_research`
ADD COLUMN `mr_begin_date` DATETIME NULL AFTER `frequency_days`;

ALTER TABLE `enford_market_research`
ADD COLUMN `mr_end_date` DATETIME NULL AFTER `mr_begin_date`;

ALTER TABLE `enford_market_research`
ADD COLUMN `remark` VARCHAR(255) NULL AFTER `mr_end_date`;

ALTER TABLE `enford_market_research`
ADD COLUMN `confirm_date` DATETIME NULL AFTER `remark`;

ALTER TABLE `enford_market_research`
ADD COLUMN `cancel_date` DATETIME NULL AFTER `confirm_date`;

ALTER TABLE `enford_market_research`
ADD COLUMN `cancel_remark` VARCHAR(255) NULL AFTER `cancel_date`;

ALTER TABLE `enford_market_research`
CHANGE COLUMN `import_id` `import_id` INT(11) NULL ,
DROP INDEX `import_id_UNIQUE` ;

ALTER TABLE `enford_market_research_dept`
ADD COLUMN `bill_number` VARCHAR(12) NULL AFTER `comp_id`;

ALTER TABLE `enford_market_research_dept`
ADD COLUMN `inside_id` INT(11) NULL AFTER `bill_number`;

ALTER TABLE `enford_market_research_dept`
ADD COLUMN `dept_code` VARCHAR(15) NULL AFTER `inside_id`;

ALTER TABLE `enford_market_research_dept`
ADD COLUMN `dept_name` VARCHAR(100) NULL AFTER `dept_code`;

ALTER TABLE `enford_market_research_commodity`
ADD COLUMN `bill_number` VARCHAR(12) NULL AFTER `cod_id`,
ADD COLUMN `inside_id` INT NULL AFTER `bill_number`,
ADD COLUMN `goods_spec` VARCHAR(15) NULL AFTER `inside_id`,
ADD COLUMN `goods_name` VARCHAR(60) NULL AFTER `goods_spec`,
ADD COLUMN `base_bar_code` VARCHAR(20) NULL AFTER `goods_name`,
ADD COLUMN `base_measure_unit` VARCHAR(20) NULL AFTER `base_bar_code`;

ALTER TABLE `enford_market_research_commodity`
CHANGE COLUMN `goods_spec` `goods_spec` VARCHAR(255) NULL DEFAULT NULL ;

set SQL_SAFE_UPDATES = 0;
update
(select a.dept_id, a.username, b.id, b.name
from enford_system_user a, enford_product_department b
where left(a.username, 4)=b.code and a.id > 15) c, enford_system_user d
set d.dept_id = c.id
where d.username = c.username;

ALTER TABLE `sg`.`enford_market_research_dept`
ADD COLUMN `bill_num` VARCHAR(12) NULL AFTER `dept_name`,
ADD COLUMN `state` INT NULL AFTER `bill_num`;

ALTER TABLE `sg`.`enford_market_research_dept`
ADD COLUMN `effective_sign` INT NULL AFTER `state`;

INSERT INTO `sg`.`enford_product_competitors` (`id`, `name`, `city_id`) VALUES ('227', '大丰大润发店', '32');
UPDATE `sg`.`enford_product_department` SET `comp_id`='227' WHERE `id`='276';
UPDATE `sg`.`enford_market_research_dept` SET `comp_id`='227' WHERE `res_id`='178' and`exe_id`='276';

INSERT INTO `sg`.`enford_product_competitors` (`id`, `name`, `city_id`) VALUES ('228', '射阳大润发店', '32');
UPDATE `sg`.`enford_product_department` SET `comp_id`='228' WHERE `id`='275';
UPDATE `sg`.`enford_market_research_dept` SET `comp_id`='228' WHERE `res_id`='185' and`exe_id`='275';

INSERT INTO `sg`.`enford_product_competitors` (`id`, `name`, `city_id`) VALUES ('229', '合肥大润发', '38');
INSERT INTO `sg`.`enford_product_competitors` (`id`, `name`, `city_id`) VALUES ('230', '马鞍山大润发', '42');
INSERT INTO `sg`.`enford_product_competitors` (`id`, `name`, `city_id`) VALUES ('231', '淮北大润发', '44');
UPDATE `sg`.`enford_product_department` SET `comp_id`='229' WHERE `id`='313';
UPDATE `sg`.`enford_product_department` SET `comp_id`='229' WHERE `id`='316';
UPDATE `sg`.`enford_product_department` SET `comp_id`='230' WHERE `id`='362';
UPDATE `sg`.`enford_product_department` SET `comp_id`='230' WHERE `id`='363';
UPDATE `sg`.`enford_product_department` SET `comp_id`='231' WHERE `id`='385';

ALTER TABLE `sg`.`enford_market_research`
ADD COLUMN `confirm_type` INT NULL DEFAULT 0 AFTER `cancel_remark`;