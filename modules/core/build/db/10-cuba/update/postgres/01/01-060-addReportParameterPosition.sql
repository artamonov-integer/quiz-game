-- $Id: 01-060-addReportParameterPosition.sql 5224 2011-07-05 05:34:27Z krivopustov $
alter table report_input_parameter  add column position integer default 0
^
alter table report_input_parameter  add column meta_class varchar(255)
^
update report_input_parameter set meta_class=screen
^
update report_input_parameter set screen = null
^
