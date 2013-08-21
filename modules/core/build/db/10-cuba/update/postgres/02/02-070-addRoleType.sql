-- $Id: 02-070-addRoleType.sql 6663 2011-12-06 06:09:40Z krivopustov $
-- Replace SEC_ROLE.IS_SUPER with SEC_ROLE.TYPE

alter table SEC_ROLE add TYPE integer
^

update SEC_ROLE set TYPE = (case when IS_SUPER = true then 10 else 0 end)
^

alter table SEC_ROLE drop IS_SUPER
^
