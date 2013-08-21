--$Id: 01-410-addDataSetParams.sql 6281 2011-10-19 16:39:03Z artamonov $
--Add fields for specify parameters in Entity/EntityList/Filter loaders

alter table REPORT_DATA_SET add ENTITY_PARAM_NAME varchar(255) ^
alter table REPORT_DATA_SET add LIST_ENTITIES_PARAM_NAME varchar(255) ^

update REPORT_DATA_SET set ENTITY_PARAM_NAME = 'entity' ^
update REPORT_DATA_SET set LIST_ENTITIES_PARAM_NAME = 'entities' ^