-- $Id: 01-240-userIpMask.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: adding SEC_USER.IP_MASK

alter table SEC_USER add column IP_MASK varchar(200);