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
