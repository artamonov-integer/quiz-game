
-- $Id: 02-090-schedulingDaylightSaving.sql 7197 2012-03-01 09:55:08Z budarov $ --
-- Description: Change scheduling column types to work normally during time shift (when clocks go back 1 hour)

alter table SYS_SCHEDULED_TASK alter column LAST_START_TIME type timestamp with time zone;
alter table SYS_SCHEDULED_EXECUTION alter column START_TIME type timestamp with time zone;
alter table SYS_SCHEDULED_EXECUTION alter column FINISH_TIME type timestamp with time zone;
