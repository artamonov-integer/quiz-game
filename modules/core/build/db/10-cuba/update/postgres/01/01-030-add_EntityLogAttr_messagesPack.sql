-- $Id: 01-030-add_EntityLogAttr_messagesPack.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: adding SEC_ENTITY_LOG_ATTR.MESSAGES_PACK field

alter table SEC_ENTITY_LOG_ATTR add MESSAGES_PACK varchar(200)
^
