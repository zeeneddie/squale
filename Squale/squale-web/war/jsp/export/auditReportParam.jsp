<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.util.SqualeWebActionUtils"%>


<bean:define id="applicationId" name="auditReportParamForm" property="applicationId" type="String"/>
<bean:define id="currentAuditId" name="auditReportParamForm" property="currentAuditId" type="String"/>
<bean:define id="previousAuditId" name="auditReportParamForm" property="previousAuditId" type="String"/>
<bean:define id="currentAuditName" name="auditReportParamForm" property="auditName" type="String"/>
<bean:define id="previousAuditName" name="auditReportParamForm" property="previousAuditName" type="String"/>
<% String auditsSelectionUrl = "audits.do?action=list&kind=terminated&applicationId=" + applicationId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId; %>

<af:page>
	<af:body>
		<af:canvasCenter titleKey="export.audit_report.title">
			<br />
			<%-- Header --%>
			<squale:resultsHeader name="auditReportParamForm" displayComparable="true"/>
			<br />
			<br />
			<%-- Help --%>
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<%--  If existing a previous audit--%>
				<logic:equal value="true" name="auditReportParamForm" property="comparableAudits">
					<bean:message key="export.audit_report_two_audits.help" arg0="<%=currentAuditName%>" arg1="<%=previousAuditName%>" arg2="<%=auditsSelectionUrl%>"/>
				</logic:equal>
				<%-- If generation will be done without comparison --%>
				<logic:notEqual value="true" name="auditReportParamForm" property="comparableAudits">
					<bean:message key="export.audit_report_one_audit.help" arg0="<%=currentAuditName%>" arg1="<%=auditsSelectionUrl%>" />
				</logic:notEqual>
			</af:dropDownPanel>
			<br />
			<jsp:include page="list_errors_common.jsp">
				<jsp:param name="title" value="export.audit_report.current_audit_errors"/>
				<jsp:param name="property" value="currentAuditErrors"/>
			</jsp:include>
			
			<%--  If existing a previous audit and if audits are comparable --%>
			<logic:equal value="true" name="auditReportParamForm" property="comparableAudits">
				<br/>
				<jsp:include page="list_errors_common.jsp">
					<jsp:param name="title" value="export.audit_report.previous_audit_errors"/>
					<jsp:param name="property" value="previousAuditErrors"/>
				</jsp:include>
			</logic:equal>
			<br/>
			<div style="color:#f00">
				<html:messages id="message"
					property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
					message="true">
					<bean:write name="message" filter="false"/>
					<br />
				</html:messages>
				<br />
			</div>
			<% String dtdUrl = "param_audit_report.do?action=uploadMappingDTD&applicationId="+applicationId+"&currentAuditId="+currentAuditId+"&previousAuditId"+previousAuditId;%>
		<af:form action="export_audit_report.do" method="POST"
				enctype="multipart/form-data">
				<html:hidden name="auditReportParamForm" property="applicationId" />
				<html:hidden name="auditReportParamForm" property="currentAuditId" />
				<html:hidden name="auditReportParamForm" property="previousAuditId" />
			<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
						border="0">
						<%-- model to upload  --%>
						<tr>
							<td class="right"><bean:message key="export.audit_report.model"/></td>
							<td valign="middle"><html:file property="model" size="50"/></td>
						</tr>
						<tr>
							<td class="right"><bean:message key="export.audit_report.presentation"/> *</td>
							<td valign="middle"><html:file property="presentation" size="50" />
							<logic:notEmpty name="presentationError">!</logic:notEmpty></td>
						</tr>
						<tr>
							<td class="right">
								<bean:message key="export.audit_report.mapping"/>
								(<af:link href="<%=dtdUrl%>" target="blank" >
									<bean:message key="export.audit_report.mapping.dtd"/>
								</af:link>) *
							</td>
							<td valign="middle"><html:file property="mapping" size="50"/>
							<logic:notEmpty name="mappingError">!</logic:notEmpty></td>
						</tr>
					</table>
				<af:buttonBar>
					<af:button name="export.audit_report.generate" callMethod="generateReport" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>