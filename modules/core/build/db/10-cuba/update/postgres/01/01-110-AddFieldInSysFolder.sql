-- $Id: 01-110-AddFieldInSysFolder.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: adding field in SYS_FOLDER

alter table SYS_FOLDER  add column DOUBLE_NAME varchar(100);