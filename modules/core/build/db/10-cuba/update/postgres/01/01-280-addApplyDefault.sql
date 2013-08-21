-- $Id: 01-280-addApplyDefault.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description:
alter table SYS_APP_FOLDER add column APPLY_DEFAULT boolean;
alter table SEC_SEARCH_FOLDER add column APPLY_DEFAULT boolean;
