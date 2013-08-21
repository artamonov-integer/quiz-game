-- $Id: 121031-dropPasswordColumnFromScheduledTask.sql 9458 2012-10-31 14:38:02Z artamonov $
-- Description: drop USER_PASSWORD column from SYS_SCHEDULED_TASK

alter table SYS_SCHEDULED_TASK drop column USER_PASSWORD^