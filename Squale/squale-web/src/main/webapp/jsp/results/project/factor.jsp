<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants"%>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.results.CriteriumForm"%>
<%@ page import="com.airfrance.squaleweb.tagslib.HistoryTag"%>	

<%-- Page de presentation des criteres / pratiques d'un facteur
		Attribut en entree :
			filter
				filtrage des resultats (true on ne montre ques les resultats calculés)
			SqualeWebConstants.SELECTED_PROJECT_KEY 			
				Clef du projet affiché
			projectSummaryForm.getResults()
				Facteur affiché (contient la liste des critères associés)
				
		Voir l'action factor de ProjectResultsAction
 --%>

<script type="text/javascript" src="jslib/manage_tab.js"></script>
<script type="text/javascript" src="jslib/information.js"></script>

<bean:define id="projectId" name="projectSummaryForm"
	property="projectId" type="String" />
<bean:define id="currentAuditId" name="projectSummaryForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="projectSummaryForm"
	property="previousAuditId" type="String" />
<bean:define id="resForm" name="projectSummaryForm" property="results" />
<bean:define id="which" name="resForm" property="id" />
<bean:define id="factorName" name="resForm" property="name"
	type="String" />
	
<%
// Parameter indicates which tab must be selected
String selectedTab = request.getParameter(HistoryTag.SELECTED_TAB_KEY);
if(selectedTab == null) {
    // First tab by default
    selectedTab = "synthese";
}
%>
	

<%-- Haut de page = info sur le facteur --%>
<af:page titleKey="project.results.title">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>

	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<squale:tracker directWay="false" projectId="<%=projectId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" />


		<af:canvasCenter>
			<%-- inclusion pour le marquage XITI spécifique à la page--%>
			<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
				<jsp:param name="page" value="Consultation::Facteur" />
			</jsp:include>

			<br />
			<squale:resultsHeader name="projectSummaryForm"
				displayComparable="true" />
			<br />
			<h2><bean:message key="project.results.criteria.subtitle"
				arg0="<%=WebMessages.getString(request, factorName)%>" /></h2>
			<br />
			<%-- Système d'onglets pour le détail des critères --%>
			<af:tabbedPane name="criteriumdetail">
				<%-- Premier onglet de synthèse --%>
				<af:tab key="project.result.factor.synthese" name="synthese"
					lazyLoading="false" isTabSelected='<%=""+selectedTab.equals("synthese")%>'>
					<br />
					<%-- 1er tableau = rappel du resultat du facteur --%>
					<table border="0" cellspacing="0" class="tblh">
						<thead>
							<tr>
								<th><bean:message key="project.result.factor.name" /></th>
								<th width="100px"><bean:message
									key="project.result.factor.value" /></th>
								<th width="100px"><bean:message
									key="project.result.factor.tendance" /></th>
								<th width="50px"></th>
								<th width="30px"></th>
							</tr>
						</thead>
						<tr>
							<td><bean:message key="<%=factorName%>" /></td>
							<td><squale:mark name="resForm" mark="currentMark" /></td>
							<td><squale:trend name="resForm" current="currentMark"
								predecessor="predecessorMark" /></td>
							<td><squale:picto name="resForm" property="currentMark" /></td>
							<td><squale:history projectId="<%=projectId%>"
								auditId="<%=currentAuditId%>" ruleId="<%=(String) which%>"
								kind="result" previousAuditId="<%=previousAuditId%>" selectedTab="synthese"/></td>
						</tr>

					</table>
					<%-- Valeur des critères du facteur --%>
					<br />
					<af:table name="<%=SqualeWebConstants.CHILDREN_KEY%>"
						property="list" displayNavigation="false" displayFooter="false"
						displayHeader="true">
						<af:cols id="criterium">
							<af:col property="name" key="project.result.criterium.name">
								<a href="#"
									onclick="javascript:F_MyOngletSelectionner( 'criteriumdetail', '<%=((ResultForm) criterium).getName()%>')">
								<bean:message name="criterium" property="name" /></a>
							</af:col>
							<af:col property="currentMark" width="100px"
								key="project.result.criterium.value">
								<squale:mark name="criterium" mark="currentMark" />
							</af:col>
							<af:col property="currentMark" width="100px"
								key="project.result.criterium.evolution">
								<squale:trend name="criterium" current="currentMark"
									predecessor="predecessorMark" />
							</af:col>
							<af:col property="currentMark" width="50px">
								<squale:picto name="criterium" property="currentMark" />
							</af:col>
							<%-- Racourci vers l'historique des criteres --%>
							<af:col property="name" width="30px">
								<bean:define name="criterium" property="id" id="id"
									type="String" />
								<squale:history projectId="<%=projectId%>"
									auditId="<%=currentAuditId%>" ruleId="<%=(String) id%>"
									kind="result" previousAuditId="<%=previousAuditId%>" selectedTab="synthese"/>
							</af:col>
						</af:cols>
					</af:table>
				</af:tab>
				<%-- Les onglets suivants correspondent a chaque critère --%>
				<logic:iterate id="criterium"
					name="<%=SqualeWebConstants.CHILDREN_KEY%>" property="list"
					type="CriteriumForm" scope="request">
					<bean:define name="criterium" property="name" id="criteriumName"
						type="String" />
					<af:tab key="<%=criteriumName%>" name="<%=criteriumName%>"
							isTabSelected='<%=""+selectedTab.equals(criteriumName)%>'>
						<br />
						<%-- 1er tableau = rappel du resultat du facteur --%>
						<table border="0" cellspacing="0" class="tblh" style="width: 100%">
							<THEAD>
								<tr>
									<th><bean:message key="project.result.factor.name" /></th>
									<th width="100px"><bean:message
										key="project.result.factor.value" /></th>
									<th width="100px"><bean:message
										key="project.result.factor.tendance" /></th>
									<th width="50px"></th>
									<th width="30px"></th>
								</tr>
							</THEAD>
							<tr>
								<td><bean:message key="<%=factorName%>" /></td>
								<td><squale:mark name="resForm" mark="currentMark" /></td>
								<td><squale:trend name="resForm" current="currentMark"
									predecessor="predecessorMark" /></td>
								<td><squale:picto name="resForm" property="currentMark" /></td>
								<td><squale:history projectId="<%=projectId%>"
									auditId="<%=currentAuditId%>" ruleId="<%=(String) which%>"
									kind="result" previousAuditId="<%=previousAuditId%>" 
									selectedTab="<%=criteriumName%>" /></td>
							</tr>
						</table>
						<br />
						<%-- 2eme tableau = rappel du resultat du critère --%>
						<table border="0" cellspacing="0" class="tblh" style="width: 100%">
							<THEAD>
								<tr>
									<th><bean:message key="project.result.criterium.name" /></th>
									<th width="100px"><bean:message
										key="project.result.criterium.value" /></th>
									<th width="100px"><bean:message
										key="project.result.criterium.evolution" /></th>
									<th width="50px"></th>
									<th width="30px"></th>
								</tr>
							</THEAD>
							<tr>
								<td><bean:message name="criterium" property="name" /></td>
								<td><squale:mark name="criterium" mark="currentMark" /></td>
								<td><squale:trend name="criterium" current="currentMark"
									predecessor="predecessorMark" /></td>
								<td><squale:picto name="criterium" property="currentMark" /></td>
								<td><%-- lien vers l'historique--%> <bean:define
									name="criterium" property="id" id="idCriterium" type="String" />
								<squale:history projectId="<%=projectId%>"
									auditId="<%=currentAuditId%>" ruleId="<%=idCriterium%>"
									kind="result" previousAuditId="<%=previousAuditId%>" 
									selectedTab="<%=criteriumName%>" /></td>
							</tr>
						</table>
						<br />
						<af:table name="criterium" property="practices"
							displayFooter="false" displayHeader="true"
							displayNavigation="false">

							<af:cols id="practice">
								<af:col property="name" width="50px">
									<squale:info name="practice" practiceName="name" ruleId="id" />
								</af:col>
								<af:col property="name" key="project.result.practice.name">
									<logic:greaterEqual name="practice" property="currentMark"
										value="0">
										<%
										            // On construit le lien vers les résultats de la pratique
										            String link =
										                "project.do?action=practice&projectId=" + projectId + "&currentAuditId=" + currentAuditId
										                    + "&previousAuditId=" + previousAuditId + "&which=" + ( (ResultForm) practice ).getId()
										                    + "&whichParent=" + ( (ResultForm) practice ).getTreParent();
										%>
										<a href="<%=link%>"> <bean:message name="practice"
											property="name" /> </a>
									</logic:greaterEqual>
									<logic:lessThan name="practice" property="currentMark"
										value="0">
										<bean:message name="practice" property="name" />
									</logic:lessThan>
								</af:col>
								<%-- On affiche la noté précédente seulement si l'audit précédent existe --%>
								<logic:notEmpty name="previousAuditId">
									<af:col property="predecessorMark" width="100px"
										key="project.result.practice.previous_value">
										<squale:mark name="practice" mark="predecessorMark" />
									</af:col>
								</logic:notEmpty>
								<af:col property="currentMark" width="100px"
									key="project.result.practice.value">
									<squale:mark name="practice" mark="currentMark" />
								</af:col>
								<af:col property="currentMark" width="100px"
									key="project.result.factor.tendance">
									<squale:trend name="practice" current="currentMark"
										predecessor="predecessorMark" />
								</af:col>
								<af:col property="currentMark" width="50px">
									<squale:picto name="practice" property="currentMark" />
								</af:col>
								<%-- Racourci vers l'historique des pratiques --%>
								<af:col property="name" width="30px">
									<bean:define name="practice" property="id" id="id"
										type="String" />
									<squale:history projectId="<%=projectId%>" ruleId="<%=id%>"
										kind="result" auditId="<%=currentAuditId%>"
										previousAuditId="<%=previousAuditId%>"
										selectedTab="<%=criteriumName%>" />
								</af:col>
							</af:cols>
						</af:table>
					</af:tab>
				</logic:iterate>
			</af:tabbedPane>
		</af:canvasCenter>
	</af:body>
</af:page>