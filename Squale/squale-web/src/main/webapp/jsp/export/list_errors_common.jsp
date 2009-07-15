<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>

<%
    String titleParam = (String) request.getParameter( "title" );
	String propertyParam = (String) request.getParameter( "property" );
%>
<table>
<td valign="top"><img src="images/pictos/warning.png" alt="warning_image" /></td>
<td><af:dropDownPanel titleKey="<%= titleParam %>">
	<logic:iterate id="setOfErrorsListForm" name="auditReportParamForm"
		property="<%=propertyParam%>">
		<bean:define id="currentProjectName" name="setOfErrorsListForm"
			property="projectName" type="String" />
		<ul>
			<af:dropDownPanel titleKey="<%=currentProjectName%>">

				<logic:iterate name="setOfErrorsListForm" property="listOfErrors"
					indexId="index" id="listOfErrorsForm">
					<bean:define name="listOfErrorsForm" property="taskName"
						id="taskName" type="String" />
					<table>
						<%-- on rassemble l'image et le dropdownpanel dans une table pour les aligner, et
									on met l'image devant car si on la met derrière alors elle est déplacée tout à droite de la page
									lorsque l'on déroule le panel --%>
						<tr>
							<td>
							<ul>
								<af:dropDownPanel titleKey="<%=taskName%>">
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
							</ul>
							</td>
					</table>
				</logic:iterate>
			</af:dropDownPanel>
		</ul>
	</logic:iterate>
</af:dropDownPanel>
</td>
</table>