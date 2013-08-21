-- $Id: 02-110-addDefaultDateIsCurrent.sql 7493 2012-04-04 12:49:59Z Novikov $
alter table SYS_CATEGORY_ATTR add column DEFAULT_DATE_IS_CURRENT boolean;