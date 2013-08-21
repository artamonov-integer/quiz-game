--$Id: 130311-renameColumnsForOracle.sql 10772 2013-03-11 06:55:47Z krivopustov $--

alter table SEC_USER drop column TYPE;

{call sp_rename 'SYS_FILE.SIZE', 'FILE_SIZE', 'column'};

{call sp_rename 'SYS_FOLDER.TYPE', 'FOLDER_TYPE', 'column'};

{call sp_rename 'SEC_ENTITY_LOG.TYPE', 'CHANGE_TYPE', 'column'};

{call sp_rename 'SEC_GROUP_HIERARCHY.LEVEL', 'HIERARCHY_LEVEL', 'column'};

{call sp_rename 'SEC_PERMISSION.TYPE', 'PERMISSION_TYPE', 'column'};

{call sp_rename 'SEC_ROLE.TYPE', 'ROLE_TYPE', 'column'};
