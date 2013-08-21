-- $Id: 02-050-reportLocales.sql 6545 2011-11-17 11:55:10Z artamonov $
-- Add localization to ReportGroup, Report, ReportInputParameter

alter table REPORT_GROUP add LOCALE_NAMES text^
alter table REPORT_REPORT add LOCALE_NAMES text^
alter table REPORT_INPUT_PARAMETER add LOCALE_NAMES text^

update REPORT_GROUP
set LOCALE_NAMES =
E'en=General \nru=Общие'
where code = 'ReportGroup.default'^