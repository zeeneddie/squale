-- #####################################################
-- Modification for the manual practice (#126)

-- Add the column timelimitation to the table qualityrule
ALTER TABLE qualityrule ADD timelimitation varchar2(6);
-- Add the column timelimitation to the table qualityresult
ALTER TABLE qualityresult ADD CreationDate date;
-- Authorize to put a null value in the column auditId of the table qualityresult
ALTER TABLE qualityresult MODIFY AuditId null;

-- #####################################################