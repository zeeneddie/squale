-- #####################################################
-- Modification for the manual practice
ALTER TABLE qualityrule ADD timelimitation varchar2(6);
ALTER TABLE qualityresult ADD CreationDate date;
ALTER TABLE qualityresult MODIFY AuditId null;

-- #####################################################