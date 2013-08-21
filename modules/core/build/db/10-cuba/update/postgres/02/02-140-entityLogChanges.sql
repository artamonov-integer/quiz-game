--$Id: 02-140-entityLogChanges.sql 8360 2012-07-19 13:26:02Z krivopustov $
-- Description: add SEC_ENTITY_LOG.CHANGES

alter table SEC_ENTITY_LOG add CHANGES text;
