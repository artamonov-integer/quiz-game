-- $Id: 01-330-CategoryAttrAlterType.sql 5722 2011-08-18 13:25:49Z gorbunkov $
-- Description:
alter table SYS_CATEGORY_ATTR alter column DATA_TYPE type varchar(200);