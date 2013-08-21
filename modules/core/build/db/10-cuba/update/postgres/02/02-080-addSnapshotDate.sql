-- $Id: 02-080-addSnapshotDate.sql 7192 2012-02-29 13:46:42Z artamonov $ Add SNAPSHOT_DATE to SYS_ENTITY_SNAPSHOT

alter table SYS_ENTITY_SNAPSHOT add column SNAPSHOT_DATE timestamp^