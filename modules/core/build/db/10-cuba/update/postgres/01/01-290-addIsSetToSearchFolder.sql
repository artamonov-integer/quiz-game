-- $Id: 01-290-addIsSetToSearchFolder.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description
alter table SEC_SEARCH_FOLDER add column IS_SET boolean;
alter table SEC_SEARCH_FOLDER add column ENTITY_TYPE varchar(50);
