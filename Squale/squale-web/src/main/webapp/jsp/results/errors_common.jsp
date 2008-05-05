<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="/squale" prefix="squale"%>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils"%>
<bean:define id="applicationId" name="errorListForm"
	property="applicationId" type="String" />
<bean:define id="projectId" name="errorListForm" property="projectId"
	type="String" />
<squale:tracker directWay="false" projectId="<%=projectId%>" />
<af:canvasCenter>
	<h1><bean:message key="project.errors.title" /></h1>
	<squale:resultsHeader name="errorListForm" />
	<br />
	<BR>
	<af:form action='<%=request.getParameter("actionErrorForm")%>'>
		<%-- Erreurs --%>
		<af:dropDownPanel titleKey="buttonTag.menu.aide">
			<bean:message key="project.results.errors.details" />
		</af:dropDownPanel>
		<br />
		<br />
		<logic:iterate name="setOfErrorsListForm" property="listOfErrors"
			indexId="index" id="listOErrorsForm">
			<bean:define name="listOErrorsForm" property="taskName" id="taskName"
				type="String" />
			<bean:define name="listOErrorsForm" property="maxErrorLevel"
				id="maxErrorLevel" type="String" />
			<%
// On récupère la valeur indiquant si le panel est ouvert ou non
String expanded = (String) request.getParameter(taskName + "Panel");
if (null == expanded) {
    expanded = "false";
}%>
			<input type="hidden" name="<%=taskName + "Panel"%>"
				id="<%=taskName + "Panel"%>" value="<%=expanded%>" />
			<table>
				<%-- on rassemble l'image et le dropdownpanel dans une table pour les aligner, et
				on met l'image devant car si on la met derrière alors elle est déplacée tout à droite de la page
				lorsque l'on déroule le panel --%>
				<tr>
					<td valign="top"><img
						src="<%=SqualeWebActionUtils.getImageForErrorLevel(maxErrorLevel)%>" />
					</td>
					<td><af:dropDownPanel titleKey="<%=taskName%>"
						expanded="<%=Boolean.valueOf(expanded).booleanValue()%>"
						onExpand="<%=\"changeVarValue('\"+taskName+\"Panel', 'true');\"%>"
						onCollapse="<%=\"changeVarValue('\"+taskName+\"Panel', 'false');\"%>">
						<%-- On ne récupère pas l'id de l'itération pour le nom de la table afin de différencier les tables pour le tri--%>
						<af:table name="setOfErrorsListForm"
							property='<%="listOfErrors[" + index + "].list"%>'
							emptyKey="table.results.none" pageLength="100">
							<af:cols id="error">
								<af:col property="level" key="project.results.error.level"
									sortable="true">
									<bean:message name="error" property="level" />
								</af:col>
								<af:col editable="true" property="message"
									key="project.results.error.message" sortable="true">
									<logic:greaterThan name="error" property="nbOcc" value="1">
										<b>(<bean:write name="error" property="nbOcc" />x)</b>
									</logic:greaterThan>
									<bean:write name="error" property="message" filter="false" />
								</af:col>
							</af:cols>
						</af:table>
					</af:dropDownPanel>
			</table>
		</logic:iterate>
		<br />
		<af:dropDownPanel titleKey="image.legend">
			<table>
				<tr>
					<td><img src="images/pictos/error.png" alt="error_image" /><bean:message
						key="error.criticity.fatal" /></td>
					<td><img src="images/pictos/warning.png" alt="warning_image" /><bean:message
						key="error.criticity.warning" /></td>
					<td><img src="images/pictos/info.png" alt="info_image" /><bean:message
						key="error.criticity.low" /></td>
				</tr>
			</table>
		</af:dropDownPanel>
	</af:form>
	<br />
	<af:buttonBar>
		<af:button name="modify.project.configuration"
			onclick="<%=\"config_project.do?action=selectProjectToModify&applicationId=\"+applicationId + \"&projectId=\"+projectId%>" />
	</af:buttonBar>
</af:canvasCenter>
<script type="text/javascript" src="jslib/manage_tab.js"></script>