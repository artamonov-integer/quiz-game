--$Id: 01-370-CategoryAttrRequired.sql 5808 2011-09-01 10:40:14Z gorbunkov $

alter table SYS_CATEGORY_ATTR add REQUIRED boolean;
update SYS_CATEGORY_ATTR set REQUIRED = false;