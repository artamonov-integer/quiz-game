--$Id: 121002-updateScreenPermissions.sql 9140 2012-10-03 05:18:30Z krivopustov $
-- Description: update screen permissions according to #1532

update sec_permission set target = substring(target, 3, len(target) - 2) where type = 10;
