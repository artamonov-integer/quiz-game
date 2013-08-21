-- $Id: 01-010-addValueIdToEntityLog.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description:

alter table SEC_ENTITY_LOG_ATTR add column VALUE_ID uuid;