<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.util.SqualeWebConstants"%>
<%@ page import="org.squale.welcom.taglib.table.InternalTableUtil" %>

<bean:define id="projectId" name="ComponentResultListForm" property="projectId"
	type="String" />
<bean:define id="projectName" name="ComponentResultListForm" property="projectName"
	type="String" />
<bean:define id="currentAuditId" name="ComponentResultListForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="ComponentResultListForm"
	property="previousAuditId" type="String" />

<af:page titleKey="project.results.title">
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<af:canvasCenter>
			<br />
			<%-- Summary of audit information --%>
			<squale:resultsHeader name="ComponentResultListForm" />
			<br />
			<bean:message key="project.results.components_with_tres.subtitle"></bean:message>
			<ul>
				<logic:iterate id="key" name="ComponentResultListForm" property="treKeys" indexId="keyId" type="String">
					<li>
						<bean:message key="<%=key%>" /> = <af:write name="ComponentResultListForm" property='<%="treValues["+keyId+"]"%>'></af:write>
					</li>
				</logic:iterate>
			</ul>
			<br />
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="project.results.components_with_tres.help" />
			</af:dropDownPanel>

			<br />
			<%-- Variables for user rights --%>
			<html:hidden name="ComponentResultListForm" property="projectId" />
			<html:hidden name="ComponentResultListForm" property="currentAuditId" />
			<html:hidden name="ComponentResultListForm" property="previousAuditId" />
			<af:table name="ComponentResultListForm" property="componentListForm.list" scope="session">
				<af:cols id="component">
					<bean:define id="fullName" name="component" property="fullName"
						type="String" />
					<bean:define id="name" name="component" property="name" type="String"/>
					<bean:define id="componentId" name="component" property="id"
						type="Long" />
					<%
							String urlComponent =  "project_component.do?action=component&projectId="
    												+ projectId
    												+ "&currentAuditId="
    												+ currentAuditId
    												+ "&previousAuditId="
    												+ previousAuditId
   													+ "&"
    												+ SqualeWebConstants.FROM_MARK_PAGE_KEY
    												+ "=true"
    												+"&component=" + componentId.toString();
						%>
					<af:col property="name" sortable="true" key="component.name"
						width="400px" contentTruncate="80">
						<a href="<%=urlComponent%>" title="<%=fullName%>">
							<%=InternalTableUtil.getTruncatedString(name, "80", "string")%></a>
					</af:col>
				</af:cols>
			</af:table>
				<%
					String urlExportExcel = "component_tres.do?action=exportComponentsToExcel&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
					String urlScatterplot = "top.do?action=displayBubble&projectId="+projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
				%>
			<af:buttonBar>
				<af:button type="form" name="retour" toolTipKey="toolTip.retour.scatterplot"
					onclick="<%=urlScatterplot%>" />
				<af:button type="form" name="export.excel"
					onclick="<%=urlExportExcel%>"
					toolTipKey="toolTip.export.excel.component" />
			</af:buttonBar>
		</af:canvasCenter>
	</af:body>
</af:page>