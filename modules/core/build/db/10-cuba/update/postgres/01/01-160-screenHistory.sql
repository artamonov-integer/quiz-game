-- $Id: 01-160-screenHistory.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description:

alter table SEC_TAB_HISTORY rename to SEC_SCREEN_HISTORY^

alter table SEC_SCREEN_HISTORY rename column CREATOR_ID to USER_ID^