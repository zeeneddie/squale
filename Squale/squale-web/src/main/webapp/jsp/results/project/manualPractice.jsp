<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.resources.WebMessages" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm" %>
<%@ page import="org.squale.squaleweb.util.graph.GraphMaker"%>

<bean:define id="projectId" name="projectSummaryForm" property="projectId" type="String" />
<bean:define id="currentAuditId" name="projectSummaryForm"	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="projectSummaryForm"	property="previousAuditId" type="String" />
<bean:define id="form" name="projectSummaryForm" property="results" />
<bean:define id="practiceName" name="form" property="name" type="String" />
<bean:define id="infoForm" name="form" property="infoForm" />

<%-- We set practiceInformationForm into the request in order to the jsp:include 
for display the practice description work --%>
<% request.setAttribute("practiceInformationForm", infoForm); %>

<af:page titleKey="project.results.title">
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
	
		<%-- The squale tracker--%>
		<squale:tracker directWay="false" projectId="<%=projectId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" />
	
		<af:canvasCenter>

			<br />
			<%-- The Squale cartridge --%>
			<squale:resultsHeader name="projectSummaryForm" displayComparable="true"/>
			<br />
			<br />
			
			<%-- Display the mark , the trend and the weather picto --%>			
			<h2><bean:message key="project.result.manualpractice.subtitle"
				arg0="<%=WebMessages.getString(request, practiceName)%>" /></h2>
			<br />
			<table class="tblh" style="width: 20%">
				<thead>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th><bean:message key="project.result.practice.value" /></th>
						<td>
							<squale:mark mark="currentMark" name="form" />
							<squale:trend name="form" current="currentMark" predecessor="predecessorMark" />
							<squale:picto name="form" property="currentMark" />
						</td>
					</tr>
				</tbody>
			</table>
			<br />
			
			<%-- Information displayed according to the audit date and the mark creation date/validity --%>
			<logic:equal name="form" property="last" value="false">
				<td >
					<img src="images/pictos/warning.png" alt="warning_image" />
					<bean:message key="project.result.manualpractice.morerecent" />
				</td>
				<br/>
				<br/>
			</logic:equal>
			<logic:equal name="form" property="outOfDate" value="true">
				<td >
					<img src="images/pictos/warning.png" alt="warning_image" />
					<bean:message key="project.result.manualpractice.outofdate" />
				</td>
				<br/>
				<br/>
			</logic:equal>
			
			<%-- Display of the practice description --%>
			<fieldset>
				<legend>
					<b><bean:message key="qualimetric_element.title" /></b>
				</legend>
				<br />
				<jsp:include page="/jsp/results/project/information_common.jsp">
					<jsp:param name="expandedDescription" value="false" />
				</jsp:include>
				<br />
			</fieldset>

		</af:canvasCenter>
	</af:body>
</af:page>