-- #####################################################
-- Modification for the manual practice
ALTER TABLE qualityrule ADD timelimitation varchar(6);
ALTER TABLE qualityresult ADD CreationDate datetime;
ALTER TABLE qualityresult MODIFY AuditId bigint null;

-- #####################################################