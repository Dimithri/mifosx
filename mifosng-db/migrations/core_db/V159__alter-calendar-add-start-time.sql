/*
this scripts adding new column 'start_time' to m_calendar table
*/

ALTER TABLE `m_calendar` ADD `start_time` SMALLINT(4) AFTER `end_date`;
