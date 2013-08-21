-- $Id: 02-020-alterDoubleValueColumnInSysAttrValueTable.sql 6460 2011-11-08 14:18:54Z devyatkin $
-- Description:
alter table SYS_ATTR_VALUE alter column DOUBLE_VALUE type numeric;
alter table SYS_CATEGORY_ATTR alter column DEFAULT_DOUBLE type numeric;