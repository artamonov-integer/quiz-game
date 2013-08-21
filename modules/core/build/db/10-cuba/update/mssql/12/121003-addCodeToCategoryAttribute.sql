--$Id: 121003-addCodeToCategoryAttribute.sql 9313 2012-10-18 06:53:14Z degtyarjov $
-- Description: add code field to CategoryAttribute

alter table SYS_CATEGORY_ATTR add column CODE varchar(50);