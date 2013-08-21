-- $Id: 01-230-reportParameterAddEnumClass.sql 5224 2011-07-05 05:34:27Z krivopustov $
-- Description:
--  * Add to 'REPORT_INPUT_PARAMETER' column 'ENUM_CLASS'

alter table REPORT_INPUT_PARAMETER add column ENUM_CLASS varchar(500);