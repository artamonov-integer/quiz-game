-- $Id: 01-340-CategoryDiscriminator.sql 5758 2011-08-26 07:00:25Z gorbunkov $
-- Description:

alter table SYS_CATEGORY add DISCRIMINATOR integer;