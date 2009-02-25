<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants" %>


<af:page titleKey="administration.not_attempted_audit.title"
	accessKey="default">
	<af:body>
		<af:canvasCenter>
			<div><html:messages id="msg" message="true" property="informations">
				<b><bean:write name="msg" /></b>
				<br>
			</html:messages></div>
			<br />
			<bean:message key="administration.not_attempted_audit.details" />
			<br />
			<br />
			<af:form action="adminNotAttemptedAudit.do" scope="session" method="POST">
				<af:table name="splitAuditsListForm" property="notAttemptedAudits"
					totalLabelPos="none" emptyKey="table.results.none">
					<af:cols id="audit" idIndex="index">
						<af:colEdit />
						<af:col property="applicationName" key="audit.application_name"
							sortable="true" type="string" editable="false" />
						<af:col key="audit.date" property="date" type="DATE"
							dateFormatKey="datetime.format.simple" sortable="true">
							<logic:equal name="audit" property="edited" value="true">
								<af:field key="empty" name="splitAuditsListForm"
									property='<%="notAttemptedAudits[" + index + "].date"%>'
									type="DATETIME" dateFormatKey="date.format.simple"
									hourFormatKey="hour.format" isRequired="true" />
							</logic:equal>
						</af:col>
					</af:cols>
				</af:table>
				<af:buttonBar>
					<af:button type="form" callMethod="changeDate" name="modify"
						toolTipKey="toolTip.modify" />
				</af:buttonBar>
			</af:form>
			<br />
			<bean:message key="administration.shutDown_audit.details" />
			<br />
			<br />
			<af:form action="purgeAuditAction.do" scope="session" method="POST"
				name="selectForm"
				type="com.airfrance.squaleweb.applicationlayer.formbean.component.AuditListForm">
				<af:table name="splitAuditsListForm" property="shutDownAudits"
					totalLabelPos="none" emptyKey="table.results.none">
					<af:cols id="audit" selectable="true">
						<af:col property="applicationName" key="audit.application_name"
							sortable="true" type="string" editable="false" />
						<af:col key="audit.date" property="date" type="DATE"
							sortable="true" dateFormatKey="datetime.format" />
					</af:cols>
				</af:table>
				<logic:notEmpty name="splitAuditsListForm" property="shutDownAudits">
					<af:buttonBar>
						<af:button type="form" callMethod="purgeRunningAudits"
							name="audits.purger" toolTipKey="toolTip.audit.purger"
							messageConfirmationKey="audits_purge.confirm" accessKey="admin" />
					</af:buttonBar>
				</logic:notEmpty>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>