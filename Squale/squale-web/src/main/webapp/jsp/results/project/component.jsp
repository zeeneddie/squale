<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page
	import="org.squale.squalecommon.enterpriselayer.businessobject.component.ComponentType"%>
<%@ page import="org.squale.squaleweb.util.SqualeWebConstants"%>
<%@ page import="org.squale.squaleweb.util.SqualeWebActionUtils"%>
<%@ page
	import="org.squale.squaleweb.applicationlayer.formbean.results.ComponentForm"%>
<%@ page
	import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO"%>

<script type="text/javascript" src="jslib/information.js"></script>
<script type="text/javascript" src="jslib/manage_tab.js"></script>
<script type="text/javascript"
	src="jslib/manage_components_comments.js"></script>

<bean:define id="form" name="componentForm" type="ComponentForm" />
<bean:define id="applicationId" name="componentForm"
	property="applicationId" type="String" />
<bean:define id="projectId" name="componentForm" property="projectId"
	type="String" />
<bean:define id="currentAuditId" name="componentForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="componentForm"
	property="previousAuditId" type="String" />

<bean:define id="componentName" name="componentForm" property="name"
	type="String" />
<bean:define id="results" name="componentForm" property="results" />
<bean:define id="practices" name="componentForm" property="practices" />
<bean:define id="componentId" name="componentForm" property="id"
	type="Long" />
<bean:define id="componentType" name="componentForm" property="type"
	type="String" />
<bean:define id="practicesList" name="practices" property="list"
	type="java.util.ArrayList" />
<bean:define id="nbPractices"
	value="<%=new Integer(practicesList.size()).toString()%>" />
<bean:define id="resultsList" name="results" property="list"
	type="java.util.ArrayList" />
<bean:define id="nbResults"
	value="<%=new Integer(resultsList.size()).toString()%>" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="userProfile"
	name="<%=org.squale.welcom.struts.util.WConstants.USER_KEY%>"
	property="<%=\"profile(\"+applicationId+\")\"%>" />

<%-- Pour les champs --%>
<%
// Pour indiquer si les champs sont en lecture seul ou pas en fonction du profile de l'utilisateur
boolean disabled = !(userProfile.equals(ProfileBO.MANAGER_PROFILE_NAME) || userProfile.equals(ProfileBO.ADMIN_PROFILE_NAME));
// On gère un message d'erreur dans le cas où le nombre de composants est trop important
String errorMsg = (String) request.getSession().getAttribute(org.squale.squaleweb.applicationlayer.action.results.project.ProjectComponentsAction.TOO_MUCH_COMPONENTS_MSG);
if (null != errorMsg) {
    pageContext.setAttribute("errorMsg", errorMsg);
}
// On récupère l'attribut de requête pour la sélection des onglets
String tabSelectedName = (String) request.getParameter("selectedTab");
// Si il n'y a pas l'attribut, on met le premier par défaut sauf dans le cas où
// l'on vient de la page mark.jsp
if (null == tabSelectedName || tabSelectedName.length() == 0) {
    tabSelectedName = "childrenTab";
    if (request.getParameter(SqualeWebConstants.FROM_MARK_PAGE_KEY) != null || form.getChildren() == null) {
        tabSelectedName = "resultsTab";
    }
    request.setAttribute("selectedTab", tabSelectedName);
}
// On récupère la valeur indiquant si le panel du filtre est ouvert ou non
String expanded = (String) request.getParameter("filterPanel");
if (null == expanded) {
    expanded = "false";
}

// On récupère la valeur du langage
String language = (String) request.getAttribute("language");
if ( language==null ) {
	language = (String) request.getParameter("language");
}
else {
	request.setAttribute("language",language);
}

// On modifie le nom du composant dans le cas d'une méthode
// 2 cas :
// Affichage des classes des paramètres sans les les packages pour le titre
String componentNameTitle = componentName;
// Espace entre chaque "," des paramètres pour l'onglet "information générale"
String componentNameInfo = componentName;
// On coupe le nom en deux à partir de la "("
String[] bracketSplit = componentName.split("\\(");
if (2 == bracketSplit.length) {
    // Il s'agit bien d'une méthode, on modifie les titres
    componentNameTitle = bracketSplit[0] + "(" + bracketSplit[1].replaceAll("[^\\.,]+\\.", "");
    componentNameInfo = componentName.replaceAll(",", ", ");
}
%>
<bean:define id="matricule"
	name="<%=org.squale.welcom.struts.util.WConstants.USER_KEY%>"
	property="matricule" type="String" />

<af:page titleKey="project.components.title">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<squale:tracker
			directWay="<%=(String) session.getAttribute(SqualeWebConstants.TRACKER_BOOL)%>"
			componentName="<%=componentName%>" projectId="<%=projectId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" />


		<af:canvasCenter>
			<%-- inclusion pour le marquage XITI spécifique à la page--%>
			<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
				<jsp:param name="page" value="Consultation::Composant" />
			</jsp:include>

			<br />
			<squale:resultsHeader name="componentForm" />
			<br />
			<h2><bean:message key="project.components.subtitle" /></h2>
			<br />

			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="component.intro" />
				<br />
				<br />
				<bean:message key="project.components.filter.details" />
			</af:dropDownPanel>
			<br />
			<br />
			<bean:message key="project.results.component.name"
				arg0="componentName" />
			<b><bean:message key="<%=componentType%>" /> <%=componentNameTitle%></b>
			<br />
			<af:form action="project_component.do">
				<input type="hidden" name="selectedTab" id="selectedTab"
					value="<%=tabSelectedName%>" />
				<input type="hidden" name="projectId" value="<%=projectId%>">
				<input type="hidden" name="currentAuditId"
					value="<%=currentAuditId%>">
				<input type="hidden" name="previousAuditId"
					value="<%=previousAuditId%>">
				<input type="hidden" name="component" value="<%=componentId%>">
				<input type="hidden" name="language" value="<%=language%>">
				<input type="hidden" name="<%="filterPanel"%>"
					id="<%="filterPanel"%>" value="<%=expanded%>" />
				<%-- On n'affiche le filtre que si le composant peut avoir des enfants --%>
				<logic:notEqual name="componentForm" property="type"
					value="<%=ComponentType.METHOD%>">
					<logic:notEqual name="componentForm" property="type"
						value="<%=ComponentType.JSP%>">
						<af:dropDownPanel titleKey="reference.filter"
							expanded="<%=Boolean.valueOf(expanded).booleanValue()%>"
							onExpand="changeVarValue('filterPanel', 'true');"
							onCollapse="changeVarValue('filterPanel', 'false');">
							<table width="50%" class="formulaire" cellpadding="0"
								cellspacing="0" border="0" align="center">
								<tr class="fondClair">
									<td><af:field property="filter"
										key="project.component.filter" /></td>
								</tr>
							</table>
							<af:buttonBar>
								<af:button type="form" name="valider" callMethod="component"
									toolTipKey="toolTip.valider" />
							</af:buttonBar>
						</af:dropDownPanel>
						<br />
					</logic:notEqual>
				</logic:notEqual>
				<%-- 
					On affiche un message d'avertissement indiquant que seulement 1000 composants ont été
					remontés si c'est le cas
				--%>
				<logic:notEmpty name="errorMsg">
					<div style="color: #f00"><bean:write name="errorMsg"
						filter="false" /> <br />
					<br />
					</div>
				</logic:notEmpty>
				<af:tabbedPane name="componentName">
					<logic:present name="componentForm" property="children">
						<af:tab key="component.children.tab" name="files"
							name="childrenTab" lazyLoading="false"
							isTabSelected='<%="" + tabSelectedName.equals("childrenTab")%>'
							onAfterShow="changeVarValue('selectedTab', 'childrenTab');">
							<br />
							<af:table name="componentForm" property="children.list"
								emptyKey="table.results.none" pageLength="30">
								<af:cols id="element">
									<af:col property="name" sortable="true"
										key="project.component.name"
										href='<%="project_component.do?action=component&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId + "&language=" + language%>'
										paramName="element" paramId="component" paramProperty="id" />
									<af:col property="type" sortable="true"
										key="project.component.type">
										<bean:message name="element" property="type" />
									</af:col>
								</af:cols>
							</af:table>
							<br />
						</af:tab>
					</logic:present>
					<af:tab key="component.results.tab" name="resultsTab"
						lazyLoading="false"
						isTabSelected='<%="" + tabSelectedName.equals("resultsTab")%>'
						onAfterShow="changeVarValue('selectedTab', 'resultsTab');">
						<br />
						<bean:message key="component.practices" />
						<br />
						<br />
						<af:table name="componentForm" property="practices.list">
							<%-- emptyKey ne marche pas --%>
							<logic:equal name="nbPractices" value="0">
								<bean:message key="table.results.none" />
							</logic:equal>
							<logic:greaterThan name="nbPractices" value="0">
								<af:cols id="element">
									<af:col property="name">
										<squale:info name="element" practiceName="name" ruleId="id" />
									</af:col>
									<af:col property="name" key="practice.name">
										<html:link
											href='<%="project.do?action=practice&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId + "&language=" + language%>'
											paramId="which" paramProperty="id" paramName="element">
											<bean:message name="element" property="name" />
										</html:link>
									</af:col>
									<af:col property="currentMark" sortable="true"
										key="practice.value">
										<squale:mark name="element" mark="currentMark" />
									</af:col>
									<af:col property="name" width="30px">
										<bean:define name="element" property="id" id="id"
											type="String" />
										<squale:history componentId="<%=componentId.toString()%>"
											auditId="<%=currentAuditId%>" ruleId="<%=id%>" kind="result"
											projectId="<%=projectId%>"
											previousAuditId="<%=previousAuditId%>" selectedTab="resultsTab"/>
									</af:col>
								</af:cols>
							</logic:greaterThan>
						</af:table>
						<br />
						<br />
						<bean:message key="component.measures" />
						<br />
						<br />
						<af:table name="componentForm" property="results.list">
							<%-- emptyKey ne marche pas --%>
							<logic:equal name="nbResults" value="0">
								<bean:message key="table.results.none" />
							</logic:equal>
							<logic:greaterThan name="nbResults" value="0">
								<af:cols id="element">
									<bean:define id="eCurrentMark" name="element"
										property="currentMark" type="String" />
									<%
// Récupération de la note
boolean matchesMark = eCurrentMark.matches("[0-9]+\\.?[0-9]*");
pageContext.setAttribute("isNumber", "" + matchesMark);%>
									<af:col property="name" key="measure.name">
										<bean:message name="element" property="name" />
									</af:col>
									<af:col property="currentMark" key="measure.value">
										<logic:equal name="isNumber" value="false">
											<bean:write name="element" property="currentMark"
												filter="false" />
										</logic:equal>
										<logic:equal name="isNumber" value="true">
											<squale:mark name="element" mark="currentMark" />
										</logic:equal>
									</af:col>
									<af:col property="" width="30px">
										<bean:define name="element" property="name" id="name"
											type="String" />
										<%-- 
											On affiche l'icone du graphe si le résultat de la métrique
											est une note ou un booléen car sinon l'évolution n'est pas
											représentative.
										--%>
										<%matchesMark |= eCurrentMark.matches("true|false");%>
										<bean:define id="isNumberOrBoolean"
											value='<%="" + matchesMark%>' />
										<logic:equal name="isNumberOrBoolean" value="true">
											<squale:history componentId="<%=componentId.toString()%>"
												ruleId="<%=name%>" projectId="<%=projectId%>"
												auditId="<%=currentAuditId%>"
												previousAuditId="<%=previousAuditId%>" selectedTab="resultsTab"/>
										</logic:equal>
									</af:col>
								</af:cols>
							</logic:greaterThan>
						</af:table>
					</af:tab>

					<af:tab key="component.info.tab" name="infoTab" lazyLoading="false"
						onAfterShow="changeVarValue('selectedTab', 'infoTab');"
						isTabSelected='<%="" + tabSelectedName.equals("infoTab")%>'>
						<br />
						<br />
						<br />
						<br />
						<bean:message key="component.info.hierarchy" />
						<br />
						<br />&nbsp;
					<b> <logic:iterate name="<%=SqualeWebConstants.GEN_INFO%>"
							property="list" scope="session" id="componentIt">
							<bean:define name="componentIt" property="id" id="compId" />
							<a
								href='<%="project_component.do?action=component&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId + "&component=" + compId.toString() + "&language=" + language %>'>
							<bean:write name="componentIt" property="name" /></a>
        					&gt;
        				</logic:iterate> <%-- Pour afficher en non cliquable le nom du composant courant
      	 				sans tout le chemin entre parenthèses--%> <%=componentNameInfo%>
						</b>
						<br />
						<br />
						<logic:equal name="componentForm" property="type"
							value="<%=ComponentType.METHOD%>">
							<logic:notEmpty name="componentForm" property="fileName">
								<bean:message key="component.info.file" />
								<br />
								<br />&nbsp;
	        				<b><bean:write name="componentForm" property="fileName" /></b>
							</logic:notEmpty>
						</logic:equal>
						<logic:equal name="componentForm" property="type"
							value="<%=ComponentType.JSP%>">
							<logic:notEmpty name="componentForm" property="fileName">
								<bean:message key="component.info.file" />
								<br />
								<br />&nbsp;
	        				<b><bean:write name="componentForm" property="fileName" /></b>
							</logic:notEmpty>
						</logic:equal>
						<br />
						<br />
						<input type="hidden" name="matricule" value="<%=matricule%>">
						<%-- il faut etre gestionnaire pour effectuer ces actions, les champs sont grisés sinon --%>
						<table class="tblh">
							<tr>
								<bean:message key="components.justification" />
								<af:field key="empty" name="componentForm"
									property="justification" type="TEXTAREA" rows="15" size="180"
									styleClassLabel="td1" disabled="<%=disabled%>" />
							</tr>
							<%-- on ne met les boutons que si il l'utilisateur est gestionnaire --%>
							<%if (!disabled) {%>
							<tr>
								<af:buttonBar>
									<af:button name="justification.comment.add"
										toolTipKey="justification.comment.add.tooltip"
										onclick="<%=\"javascript:add_comment_template('\"+ SqualeWebActionUtils.getTodayDate(request.getLocale(), \"datetime.format.component\")+\"')\"%>" />
									<af:button name="justification.comment.erase"
										toolTipKey="justification.comment.erase.tooltip"
										onclick="javascript:erase_comment()" />
								</af:buttonBar>
							</tr>
							<%}%>
						</table>
						<br />
						<%-- si ya pas de résultats on grise le champ car on ne peut pas l'exclure --%>
						<logic:equal name="nbResults" value="0">
							<af:field key="components.excluded" name="componentForm"
								property="excludedFromActionPlan" type="CHECKBOX"
								styleClassLabel="td1" disabled="true" />
						</logic:equal>
						<%-- si il y a des résultats , il faut etre gestionnaire de l'application sinon le champ est grisé --%>
						<logic:notEqual name="nbResults" value="0">
							<af:field key="components.excluded" name="componentForm"
								property="excludedFromActionPlan" type="CHECKBOX"
								styleClassLabel="td1" disabled="<%=disabled%>" />
						</logic:notEqual>
						<%-- on ne met les boutons que si l'utilisateur est gestionnaire --%>
						<%if (!disabled) {%>
						<af:buttonBar>
							<af:button name="justification.valid"
								toolTipKey="justification.valid.tooltip" callMethod="update" />
						</af:buttonBar>
						<%}%>
					</af:tab>
				</af:tabbedPane>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>