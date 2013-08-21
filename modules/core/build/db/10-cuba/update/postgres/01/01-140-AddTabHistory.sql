-- $Id: 01-140-AddTabHistory.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description:
create table SEC_TAB_HISTORY (
	ID uuid,
	CREATE_TS timestamp,
	CREATED_BY varchar(50),
	CREATOR_ID uuid,
	CAPTION varchar(255),
	URL varchar(4000),
	primary key (ID)
);

alter table SEC_TAB_HISTORY add constraint FK_SEC_HISTORY_USER foreign key (CREATOR_ID) references SEC_USER (ID);
