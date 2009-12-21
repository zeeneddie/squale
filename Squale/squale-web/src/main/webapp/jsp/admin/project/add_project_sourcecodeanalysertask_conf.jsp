<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %><bean:define
	id="projectId" name="createProjectForm" property="projectId"
	type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<sec:notHasProfile var="disabled" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />

<br />
<bean:message key="project_creation.analyser_conf.details" />
<br />
<br />
<%-- Configuration des paramètres généraux du récupérateur de sources --%>
<af:form action="add_project_analyser_config.do">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">

	<table width="100%" class="tblh" cellpadding="0" cellspacing="0"
		border="0">
		<%-- Chemin vers la racine du projet --%>
		<tr>
			<af:field key="project_creation.analyser.field.path" property="path"
				isRequired="true" disabled="<%=disabled%>" size="100"
				styleClassLabel="td1" />
		</tr>
	</table>

	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</sec:ifHasProfile>
</af:form>