-- $Id: 01-120-AddCodeFieldInSecFilter.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: adding field in SEC_FILTER

alter table SEC_FILTER  add column CODE varchar(200);