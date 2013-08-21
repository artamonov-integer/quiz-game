-- $Id: 01-410-addStartDateToUserSubstitution.sql 6424 2011-11-01 09:21:05Z chernov $
-- Description:

alter table SEC_USER_SUBSTITUTION add column START_DATE timestamp;