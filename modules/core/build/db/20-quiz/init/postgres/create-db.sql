----- QUIZ_ANSWER creation -----
create table QUIZ_ANSWER (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    CONTENT varchar(255),
    primary key (ID)
)^

----- QUIZ_QUESTION creation -----
create table QUIZ_QUESTION (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    CONTENT varchar(255),
    ANSWER uuid,
    IMAGE uuid,
    primary key (ID)
)^

alter table QUIZ_QUESTION add constraint FK_QUIZ_QUESTION_TO_QUIZ_ANSWER
foreign key (ANSWER) references QUIZ_ANSWER(ID)^

alter table QUIZ_QUESTION add constraint FK_QUIZ_QUESTION_TO_SYS_FILE
foreign key (IMAGE) references SYS_FILE(ID)^

alter table quiz_question add column STAGE integer^

alter table QUIZ_QUESTION add column IMAGE_MID uuid^
alter table QUIZ_QUESTION add column IMAGE_HIGH uuid^

alter table QUIZ_QUESTION add constraint FK_QUIZ_QUESTION_MID_TO_SYS_FILE
foreign key (IMAGE_MID) references SYS_FILE(ID)^

alter table QUIZ_QUESTION add constraint FK_QUIZ_QUESTION_HIGH_TO_SYS_FILE
foreign key (IMAGE_HIGH) references SYS_FILE(ID)^

----- QUIZ_SCORE creation -----
create table QUIZ_SCORE (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    POINTS integer,
    TYPE integer,
    USER_ID uuid,
    primary key (ID)
)^

alter table QUIZ_SCORE add constraint FK_QUIZ_SCORE_TO_SEC_USER
foreign key (USER_ID) references SEC_USER(ID)^


create unique index IDX_QUIZ_ANSWER_CONTENT on QUIZ_ANSWER(CONTENT) where DELETE_TS is null^

