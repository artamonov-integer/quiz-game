-- $Id: 01-180-changeAppFoldersScriptsLength.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: change type of script columns
ALTER TABLE sys_app_folder ALTER COLUMN visibility_script TYPE text;
ALTER TABLE sys_app_folder ALTER COLUMN quantity_script TYPE text;