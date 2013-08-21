-- $Id: 01-250-entityStatLookupScreenThreshold.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: adding SYS_ENTITY_STATISTICS.LOOKUP_SCREEN_THRESHOLD

alter table SYS_ENTITY_STATISTICS add column LOOKUP_SCREEN_THRESHOLD integer;