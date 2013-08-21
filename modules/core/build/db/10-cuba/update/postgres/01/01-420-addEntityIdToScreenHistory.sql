-- $Id: 01-420-addEntityIdToScreenHistory.sql 6449 2011-11-03 15:12:58Z gorbunkov $

alter table SEC_SCREEN_HISTORY add ENTITY_ID uuid;