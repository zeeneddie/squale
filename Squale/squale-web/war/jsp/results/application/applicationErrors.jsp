<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page
	import="org.squale.squaleweb.applicationlayer.formbean.component.ProjectForm"%>
<%@ page import="org.squale.squaleweb.resources.WebMessages"%>

<script type="text/javascript" src="jslib/information.js"></script>
<script type="text/javascript"
	src="theme/charte_v03_001/js/tagManagement.js"></script>
<script type="text/javascript" src="jslib/jquery.js"></script>
<script type="text/javascript" src="jslib/jquery-ui.js"></script>
<script type="text/javascript" src="theme/charte_v03_001/js/exporter.js"></script>

<bean:define id="applicationName" name="applicationErrorForm"
	property="applicationName" type="String" />
<bean:define id="applicationId" name="applicationErrorForm"
	property="applicationId" type="String" />
<bean:define id="currentAuditId" name="applicationErrorForm"
	property="currentAuditId" type="String" />
<bean:define id="currentAuditDate" name="applicationErrorForm"
	property="auditDate" type="String" />
<bean:define id="previousAuditId" name="applicationErrorForm"
	property="previousAuditId" type="String" />

<af:page titleKey="project.errors.title" accessKey="default">
	<af:body canvasLeftPageInclude="/jsp/canvas/application_menu.jsp">
		<squale:tracker directWay="false" applicationId="<%=applicationId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" />
		<af:canvasCenter>
			<br />
			<squale:resultsHeader name="applicationErrorForm" />
			<br />
			<br />
			<!-- Erreurs -->
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="project.results.errors.details" />
			</af:dropDownPanel>
			<br />
			<!-- On affiche un message de confirmation dans le cas d'une reprogrammation -->
			<div style="color: #f00"><html:messages id="msg" message="true">
				<bean:write name="msg" />
				<br>
			</html:messages></div>
			<br />
			<br />
			<af:table name="applicationErrorForm" property="list"
				emptyKey="table.results.none" scope="session">
				<af:cols id="error">
					<bean:define id="projectId" name="error" property="id" />
					<%
					                String href =
					                "application_errors.do?action=errors&currentAuditId=" + currentAuditId + "&previousAuditId="
					                    + previousAuditId;
					%>
					<af:col property="projectName"
						key="application.errors.project.name" sortable="true"
						href="<%=href%>" paramName="error" paramId="projectId"
						paramProperty="id" />

					<af:col
						property='<%="errorsRepartition["+ProjectForm.ERROR_ID+"]"%>'
						key='<img align="absmiddle" border="0" src="images/pictos/error.png" />'
						sortable="true" />

					<af:col
						property='<%="errorsRepartition["+ProjectForm.WARNING_ID+"]"%>'
						key='<img align="absmiddle" border="0" src="images/pictos/warning.png" />'
						sortable="true" />

					<af:col
						property='<%="errorsRepartition["+ProjectForm.INFO_ID+"]"%>'
						key='<img align="absmiddle" border="0" src="images/pictos/info.png" />'
						sortable="true" />

					<af:col property='' key='application.errors.project.tasks'>
						<bean:size id="nbFailed" name="error" property="failedTasks" />
						<logic:equal name="nbFailed" value="1">
							<bean:define id="firstTaskName" name="error"
								property="failedTasks[0]" type="String" />
							<a
								href='<%=href+"&projectId="+projectId+"&"+firstTaskName+".namePanel=true"%>'><bean:message
								key='<%=firstTaskName+".name"%>' /></a>
						</logic:equal>
						<logic:greaterThan name="nbFailed" value="1">
							<ul>
								<logic:iterate name="error" property="failedTasks" id="taskName"
									type="String">
									<li><a
										href='<%=href+"&projectId="+projectId+"&"+taskName+".namePanel=true"%>'><bean:message
										key='<%=taskName+".name"%>' /></a></li>
								</logic:iterate>
							</ul>
						</logic:greaterThan>
					</af:col>
				</af:cols>
			</af:table>
			<br />
			<af:form action="failed_audit.do" scope="session" method="POST">
				<input type="hidden" name="applicationId" value="<%=applicationId%>" />
				<input type="hidden" id="applicationName" name="applicationName"
					value="<%=applicationName%>" />
				<input type="hidden" id="auditDate" name="auditDate"
					value="<%=currentAuditDate%>" />
				<bean:parameter id="oldPreviousAuditId" name="oldPreviousAuditId"
					value="none" />
				<input type="hidden" id="oldPreviousAuditId"
					name="oldPreviousAuditId" value="<%=oldPreviousAuditId%>" />
				<bean:parameter id="oldAuditId" name="oldAuditId" value="none" />
				<input type="hidden" id="oldAuditId" name="oldAuditId"
					value="<%=oldAuditId%>" />
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr align="center">
						<td><af:button type="form" callMethod="purgeFailedAudit"
							name="audits.purger" toolTipKey="toolTip.failed_audit.purge"
							messageConfirmationKey="failed_audit_purge.confirm"
							accessKey="manager" /></td>
						<td><af:button type="form" callMethod="restart"
							name="audit.restart" toolTipKey="toolTip.failed_audit.restart"
							accessKey="manager" /></td>
					</tr>
				</table>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>