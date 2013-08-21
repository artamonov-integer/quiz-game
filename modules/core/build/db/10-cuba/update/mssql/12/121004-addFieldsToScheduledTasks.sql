--$Id: 121004-addFieldsToScheduledTasks.sql 9286 2012-10-14 17:17:12Z degtyarjov $
-- Description: add defined by, class name and script name fields to scheduled task
-- also update all existing tasks to beans

alter table SYS_SCHEDULED_TASK add column DEFINED_BY varchar(1) default 'B';
alter table SYS_SCHEDULED_TASK add column CLASS_NAME varchar(500);
alter table SYS_SCHEDULED_TASK add column SCRIPT_NAME varchar(500);

