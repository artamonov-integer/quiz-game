-- $Id: 01-020-dropDbUpdatePKey.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: Moving to the new update database mechanism

alter table SYS_DB_UPDATE drop constraint SYS_DB_UPDATE_PKEY;

