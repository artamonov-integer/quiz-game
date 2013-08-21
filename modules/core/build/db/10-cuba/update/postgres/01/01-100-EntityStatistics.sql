-- $Id: 01-100-EntityStatistics.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description: adding SYS_ENTITY_STATISTICS table

create table SYS_ENTITY_STATISTICS (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    NAME varchar(50),
    INSTANCE_COUNT bigint,
    MAX_FETCH_UI integer,
    LAZY_COLLECTION_THRESHOLD integer,
    primary key (ID)
)^
