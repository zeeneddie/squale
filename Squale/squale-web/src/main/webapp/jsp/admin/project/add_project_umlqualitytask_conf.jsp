<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<sec:notHasProfile var="disabledObj" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />
<%boolean disabled = ((Boolean)pageContext.getAttribute("disabledObj")).booleanValue();%>

<br />

<%-- Configuration des paramètres de umlquality --%>

<bean:message key="project_creation.umlquality_conf.details" />
<br />
<bean:message key="project_creation.umlquality.exclude.details" />

<af:form action="add_project_umlquality_config.do">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">


	<br />
	<%-- Configuration du paramètre chemin du fichier de modèle XMI --%>
	<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
		border="0">
		<tr>
			<af:field key="project_creation.umlquality.field.xmi_file_location"
				property="xmiFile" size="60" isRequired="true" styleClassLabel="td1"
				filterSpecialChar="true" disabled="<%=disabled%>" />
		</tr>
	</table>
	<%-- Configuration des patterns des classes à exclure de l'analyse--%>
	<table id="excludedUMLPatternsTable" width="100%" class="tblh"
		cellpadding="0" cellspacing="0" border="0">
		<THEAD>
			<tr>
				<th></th>
				<th style="width: 50%"><bean:message
					key="project_creation.exclude.analysis.title" /></th>
			</tr>
		</THEAD>
		<tr style="display: none">
			<af:field styleClassLabel="td1"
				key="project_creation.exclude.analysis.title"
				property="excludeUMLPatterns" value="" size="60" />
		</tr>
		<squale:iteratePaths name="umlQualityForm"
			property="excludeUMLPatterns"
			key="project_creation.exclude.analysis.title"
			disabled="<%=disabled%>" />
	</table>
	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<af:buttonBar>
			<af:button onclick="addField('excludedUMLPatternsTable', 1);"
				name="add_ex_anal_line" singleSend="false" />
		</af:buttonBar>
		<jsp:include page="common_parameters_buttons.jsp" />
	</sec:ifHasProfile>
</af:form>