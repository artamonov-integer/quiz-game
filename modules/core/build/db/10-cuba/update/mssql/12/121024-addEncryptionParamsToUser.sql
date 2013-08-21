-- $Id: 121024-addEncryptionParamsToUser.sql 9406 2012-10-26 08:58:56Z artamonov $
-- Description: add SALT, HASH_METHOD, CHANGE_PASSWORD_AT_LOGON fields

alter table SEC_USER alter column PASSWORD varchar(40)^

alter table SEC_USER add CHANGE_PASSWORD_AT_LOGON tinyint^

alter table SEC_USER add SALT varchar(16)^