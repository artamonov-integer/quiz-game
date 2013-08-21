--$Id: 02-110-addUserDescriminator.sql 7871 2012-05-18 13:01:16Z shishov $

alter table SEC_USER add TYPE varchar(1)^

update SEC_USER set TYPE = 'C'^