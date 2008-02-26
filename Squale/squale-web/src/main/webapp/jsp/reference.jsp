<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.LogonBean" %>
<%@ page import="com.airfrance.welcom.struts.util.WConstants" %>
<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages" %>

<%
// Récupération de l'utilisateur en session pour savoir si celui-ci est administrateur
// SQUALE
LogonBean sessionUser = (LogonBean) request.getSession().getAttribute(WConstants.USER_KEY);
boolean isAdmin = sessionUser.isAdmin();
%>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>


<af:page titleKey="reference.title" accessKey="default">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body>	
	
		<%-- inclusion pour le marquage XITI spécifique à la page--%>
		<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
			<jsp:param name="page" value="Divers::referentiel" />
		</jsp:include>
		
		<af:canvasCenter>
			<br />

			<af:dropDownPanel titleKey="buttonTag.menu.aide">

				<%if (!isAdmin) {%>
				<bean:message key="reference.summary" />
				<br />
				<bean:message key="reference.user.projects" />
				<br />
				<%} //isAdmin
else {%>
				<bean:message key="reference.subtitle" />
				<%}%>
			</af:dropDownPanel>

			<af:form action="reference.do">
				<div style="color: #f00"><html:messages id="message"
					property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
					message="true">
					<bean:write name="message" />
					<br />
				</html:messages> <br />
				</div>
				<%-- on teste juste pour savoir si on a des résultats, si pas de résultats on le signale --%>
				<bean:size id="size" name="setOfReferencesListForm" property="list" />
				<logic:equal value="0" name="size">
					<table>
						<tr>
							<td><bean:message key="table.results.none" /></td>
						</tr>
					</table>
				</logic:equal>
				<%-- il y a des résultats, on va les afficher par grille --%>
				<logic:greaterThan name="size" value="0">
					<af:tabbedPane>
						<logic:iterate id="gridResultGlobale"
							name="setOfReferencesListForm" property="list" indexId="index">
							<bean:define id="gridName" property="name"
								name="gridResultGlobale" type="String" />
							<%-- on affiche un onglet par nom de grille différent --%>
							<af:tab key='<%="ref." + gridName%>' name='<%=gridName%>'
								lazyLoading="true">
								<%-- On affiche les références pour la version la plus récente, en première position --%>
								<br />
								<br />
								<bean:message key="grid.updateDate" />
								<bean:write name="gridResultGlobale" property="formattedDate" />
								<br />
								<br />
								<af:table name='setOfReferencesListForm'
									property='<%="list[" + index + "].referenceListForm.list"%>'
									displayNavigation="false">
									<%-- emptyKey ne marche pas --%>
									<af:cols id="element" idIndex="idElement">
										<%
// Si l'utilisateur est admin, on spécifie si l'élément est masqué ou pas
if (isAdmin) {%>
										<af:colSelect />
										<af:col property="state" key="reference.state" sortable="true" />
										<%}%>
										<%-- On écrit en gras le nom des applications et projets dont on est gestionnaire ou lecteur --%>
										<bean:define id="applicationName" name="element"
											property="applicationName" type="String" />
										<bean:define id="isPublic" name="element" property="public"
											type="Boolean" />
										<%
// Si l'utilisateur est gestionnaire ou lecteur, on écrit en gras
if (!isAdmin && (isPublic.booleanValue() || !applicationName.equals(WebMessages.getString(request, "reference.project.anonyme")))) {%>
										<af:col property="applicationName" key="application.name"
											sortable="true">
											<B> <%=applicationName%> </B>
										</af:col>
										<af:col property="name" key="project.name" sortable="true">
											<bean:define id="projectName" name="element" property="name" />
											<B> <%=projectName%> </B>
										</af:col>
										<%} else {
    // on écrit la valeur normale, qui peut etre un - si on est pas admin%>
										<af:col property="applicationName" key="application.name"
											sortable="true" />
										<af:col property="name" key="project.name" sortable="true" />
										<%}%>
										<%-- ecrit les différentes données référencées  de type volumétrié--%>
										<af:col property="numberOfMethods"
											key="mccabe.project.numberOfMethods" sortable="true"
											type="NUMBER" />
										<af:col property="numberOfClasses"
											key="mccabe.project.numberOfClasses" sortable="true"
											type="NUMBER" />
										<af:col property="numberOfCodeLines" key="mccabe.project.sloc"
											sortable="true" type="NUMBER" />
										<%-- écrit les données référencées par rapport à la grille qualité --%>
										<logic:iterate id="factor" name="element" property="factors">
											<af:col property="value"
												key="<%=((ProjectFactorForm) factor).getName()%>">
												<squale:mark name="factor" mark="currentMark" />
												<squale:picto name="factor" property="currentMark" />
											</af:col>
										</logic:iterate>
									</af:cols>
								</af:table>
								<br />
								<br />
							</af:tab>
						</logic:iterate>
					</af:tabbedPane>
					<af:buttonBar>
						<af:button type="form" name="reference.updateReferentiel"
							toolTipKey="toolTip.reference.updateReferentiel"
							callMethod="updateReferentiel" accessKey="admin"
							overridePageAccess="true" />
					</af:buttonBar>
				</logic:greaterThan>
				<br />
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>