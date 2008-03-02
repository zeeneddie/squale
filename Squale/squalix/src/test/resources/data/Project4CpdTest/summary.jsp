<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils" %>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.AuditForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.AuditListForm" %>
<%@ page import="java.util.List" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm" %>

<%
String directComponentWay = (String) session.getAttribute(SqualeWebConstants.TRACKER_BOOL);
ComponentForm component = (ComponentForm) session.getAttribute("componentForm");
ProjectSummaryForm projectSum = (ProjectSummaryForm) session.getAttribute("ProjectSummaryForm");
String errorLink = WebMessages.getString(request,"errors.consult");
%>

<bean:define id="auditDate" name="<%=com.airfrance.squaleweb.applicationlayer.controller.SqualeRequestProcessor.REQUEST_DTO%>" property="currentAuditDto.formattedDate" type="String" />

<script type="text/javascript" src="/squale/jslib/information.js"></script>
<script type="text/javascript" src="/squale/jslib/help_bubble.js"></script>

<af:page titleKey="project.results.title"
	subTitleKey="project.results.summary.subtitle">
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<af:canvasCenter>
			<br />
			<br />
			<bean:define id="applicationName"
				name="<%=SqualeWebConstants.SELECTED_APPLICATION_KEY%>"
				property="name" type="String" />
			<b><bean:message key="description.name.application"
				arg0="<%=applicationName%>" /></b>
			<br />
			<bean:define id="projectName"
				name="<%=SqualeWebConstants.SELECTED_PROJECT_KEY%>" property="name"
				type="String" />
			<b><bean:message key="description.name.project"
				arg0="<%=projectName%>" /></b>
			<br />
			<b><bean:message key="description.name.audit" arg0="<%=auditDate%>" /></b>
			<br />
			<br />
			<logic:equal name="projectSummaryForm" property="haveErrors" 
				scope="session" value="true">
				<a href="errors.do?action=errors"> 
					<B><U><%=errorLink %></U></B><BR><BR>
				</a>
			</logic:equal>
			<af:tabbedPane name="projectsummary">
				<!-- Kiviat -->
				<af:tab key="project.results.kiviat.tab" name="kiviat">
					<br />
					<br />
					<bean:message key="project.results.kiviat.details" />
					<br />
					<br />
					<%String imageDetails1 = WebMessages.getString(request, "image.project.factors");%>
					<div align="center"><img
						onmouseover="displayHelp('<%=imageDetails1%>')"
						onmouseout="hideHelp()" src="/squale/project.do?action=kiviat" />
					</div>
					<br />
					<br />
				</af:tab>
				<!-- Facteurs et tendances -->
				<af:tab key="project.results.factors.tab" name="factors">
					<br />
					<br />
					<bean:message key="project.results.factors.details" />
					<br />
					<br />
					<af:form action="project.do?action=summary&selected=factors">
						<af:table name="projectSummaryForm" property="factors.factors" scope="session">
							<af:cols idIndex="index" id="factor">
								<af:col key="project.result.factor.name" property="name"
									width="150px">
									<html:link href="/squale/project.do?action=factor"
										paramId="which" paramProperty="id" paramName="factor">
										<bean:message name="factor" property="name" />
									</html:link>
								</af:col>
								<af:col key="project.result.factor.value" property="currentMark"
									width="150px">
									<squale:mark name="factor" mark="currentMark" />
								</af:col>
								<af:col key="project.result.factor.tendance" property="currentMark"
									width="150px">
									<squale:trend name="factor" current="currentMark" predecessor="predecessorMark" />
								</af:col>
								<af:col key="empty" property="currentMark" width="150px" paramId="param">
									<%-- Recupere l'image --%>
									<squale:picto name="factor" property="currentMark"/>
									</af:col>
							</af:cols>
						</af:table>
					</af:form>
					<br />
					<br />
				</af:tab>
				<!-- Scatterplot -->
				<af:tab key="project.results.scatterplot.tab" name="scatterplot">
					<br />
					<br />
					<bean:message key="project.results.scatterplot.details" />
					<br />
					<br />
					<%String imageDetails2 = WebMessages.getString(request, "image.project.scatterplot");%>
					<div align="center"><img
						onmouseover="displayHelp('<%=imageDetails2%>')"
						onmouseout="hideHelp()"
						src="/squale/project.do?action=scatterplot" /></div>
					<br />
					<br />
				</af:tab>
				<!-- Volumétrie -->
				<af:tab key="project.results.volumetry.tab" name="volumetry">
					<br />
					<br />
					<bean:message key="project.results.volumetry.details" />
					<br />
					<br />
					<af:table name="projectSummaryForm" property="volumetry.list"
						scope="session" emptyKey="table.results.none">
						<af:cols id="element">
							<af:col property="name" key="measure.name">
								<bean:message name="element" property="name" />
							</af:col>
							<af:col property="currentMark" key="measure.value" />
						</af:cols>
					</af:table>
					<br />
					<br />
				</af:tab>
			</af:tabbedPane>
		</af:canvasCenter>
		<!-- une autre valeur que "true" indique qu'on est passé par une autre vue 
			que celle composant directement -->
		<bean:define id="componentName" type="String"  property="name" value="<%=component%> "/>
		<squale:tracker directWay="<%=(String) session.getAttribute(SqualeWebConstants.TRACKER_BOOL)%>" 
			componentName="<%=componentName%>" projectId="<%=projectId%>" />
	</af:body>
</af:page>