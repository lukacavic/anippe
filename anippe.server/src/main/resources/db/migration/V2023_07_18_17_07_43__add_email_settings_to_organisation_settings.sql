alter table organisation_settings add column email_smtp_server character varying;
alter table organisation_settings add column email_smtp_username character varying;
alter table organisation_settings add column email_smtp_password character varying;
alter table organisation_settings add column email_smtp_port smallint;
alter table organisation_settings add column email_smtp_protocol smallint;
alter table organisation_settings add column email_smtp_email character varying;
alter table organisation_settings add column email_global_bcc character varying;
alter table organisation_settings add column email_predefined_header_footer character varying default '{"_type": "EmailPredefinedHeaderFooterDoMapEntity"}';
