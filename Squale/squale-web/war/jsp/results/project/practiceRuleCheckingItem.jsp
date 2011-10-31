<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.util.SqualeWebConstants" %>
<%@ page import="org.squale.squaleweb.resources.WebMessages" %>

<script type="text/javascript"
	src="theme/charte_v03_001/js/tagManagement.js"></script>
<script type="text/javascript" src="jslib/jquery.js"></script>

<%-- Affiche les détails d'une transgression --%>

<bean:define id="projectId" name="projectSummaryForm"
	property="projectId" type="String" />
<bean:define id="currentAuditId" name="projectSummaryForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="projectSummaryForm"
	property="previousAuditId" type="String" />

<bean:define id="practiceName" name="practiceName" type="String" />

<af:page titleKey="project.results.title">
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<squale:tracker directWay="false" projectId="<%=projectId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" />
		<af:canvasCenter>
			<br />
			<squale:resultsHeader name="projectSummaryForm" displayComparable="true"/>
			<br />
			<h2><bean:message key="project.results.practice.subtitle"
				arg0="<%=WebMessages.getString(request, practiceName)%>" /></h2>
			<br />
			<bean:define name="<%=SqualeWebConstants.ITEMS_KEY%>"
				property="ruleName" id="ruleName" type="String" />
			<h3><bean:message
				key="project.result.practice.rulesChecking.rule.item.title"
				arg0="<%=ruleName%>" /></h3><br/>
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<br />
				<bean:message
					key="project.result.practice.rulesChecking.rule.item.intro"
					arg0="<%=ruleName%>" />
			</af:dropDownPanel>
			<br />
			<af:table name="<%=SqualeWebConstants.ITEMS_KEY%>" property="details"
				totalLabelPos="none">
				<af:cols id="itemForm">
					<%-- 
						On affiche les colonnes des composants seulement si au moins un item possède
						un lien vers un composant.
					--%>
					<logic:equal name="<%=SqualeWebConstants.ITEMS_KEY%>"
						property="componentLink" value="true">
						<logic:equal name="itemForm" property="componentId" value="-1">
							<af:col property="componentName" sortable="true"
								key="project.result.practice.rulesChecking.rule.item.component">
							</af:col>
						</logic:equal>
						<logic:notEqual name="itemForm" property="componentId" value="-1">
							<af:col property="componentName" sortable="true"
								key="project.result.practice.rulesChecking.rule.item.component"
								href='<%="project_component.do?action=component&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId%>'
								paramName="itemForm" paramProperty="componentId"
								paramId="component">
							</af:col>
						</logic:notEqual>
						<logic:equal name="itemForm" property="componentInvolvedId"
							value="-1">
							<af:col property="componentInvolvedName" sortable="true"
								key="project.result.practice.rulesChecking.rule.item.component_involved">
							</af:col>
						</logic:equal>
						<logic:notEqual name="itemForm" property="componentInvolvedId"
							value="-1">
							<af:col property="componentInvolvedName" sortable="true"
								key="project.result.practice.rulesChecking.rule.item.component_involved"
								href='<%="project_component.do?action=component&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId%>'
								paramName="itemForm" paramProperty="componentInvolvedId"
								paramId="component">
							</af:col>
						</logic:notEqual>
					</logic:equal>
					<af:col property="message"
						key="project.result.practice.rulesChecking.rule.item.message"
						sortable="true">
					</af:col>
				</af:cols>
			</af:table>
			<br />
			<br />
		</af:canvasCenter>
	</af:body>
</af:page>