-- $Id: 02-100-entitySnapshotAuthor.sql 7364 2012-03-27 13:10:08Z krivopustov $
-- Description: core$EntitySnapshot.author attribute added

alter table SYS_ENTITY_SNAPSHOT add AUTHOR_ID uniqueidentifier^

alter table SYS_ENTITY_SNAPSHOT
add constraint FK_SYS_ENTITY_SNAPSHOT_AUTHOR_ID foreign key (AUTHOR_ID) references SEC_USER(ID)^