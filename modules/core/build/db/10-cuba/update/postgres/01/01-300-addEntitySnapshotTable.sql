-- $Id: 01-300-addEntitySnapshotTable.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: add table SYS_ENTITY_SNAPSHOT

create table SYS_ENTITY_SNAPSHOT (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    ENTITY_META_CLASS varchar(50),
    ENTITY_ID uuid,
    VIEW_XML text,
    SNAPSHOT_XML text,
	primary key (ID)
)^
