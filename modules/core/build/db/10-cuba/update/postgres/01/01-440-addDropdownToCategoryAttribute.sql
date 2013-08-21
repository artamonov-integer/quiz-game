-- $Id: 01-440-addDropdownToCategoryAttribute.sql 7481 2012-04-04 11:08:58Z Novikov $

alter table SYS_CATEGORY_ATTR add column LOOKUP boolean;
