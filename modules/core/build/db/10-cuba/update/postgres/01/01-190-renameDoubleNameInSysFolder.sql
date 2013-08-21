-- $Id: 01-190-renameDoubleNameInSysFolder.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: Rename double_name column to tab_name
ALTER TABLE sys_folder RENAME COLUMN double_name to tab_name;