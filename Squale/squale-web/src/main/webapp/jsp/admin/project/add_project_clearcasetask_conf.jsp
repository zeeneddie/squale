<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="userProfile"
	name="<%=org.squale.welcom.struts.util.WConstants.USER_KEY%>"
	property='<%="profile("+applicationId+")"%>' />
<%-- Pour les champs --%>
<%boolean disabled = false; // Sert à interdire ou non l'écriture des champs %>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true;%>
</logic:equal>

<br />
<br />
<%-- Configuration des paramètres généraux de clearcase --%>
<af:form action="add_project_clearcase_config.do">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">

	<table width="100%" class=formulaire cellpadding="0" cellspacing="0"
		border="0">

		<%-- branche clearcase --%>
		<tr class="fondClair">
			<af:field key="project_creation.field.branch" name="clearCaseForm"
				property="labelAudited" isRequired="true" styleClassLabel="td1"
				size="60" disabled="<%=disabled%>" />
		</tr>
		<%-- appli clearcase --%>
		<tr>
			<af:field key="project_creation.field.appli" name="clearCaseForm"
				property="appli" isRequired="true" styleClassLabel="td1" size="60"
				disabled="<%=disabled%>" />
		</tr>
	</table>

	<%-- vobs à charger --%>
	<table id="vobsTable" width="100%" class="formulaire" cellpadding="0"
		cellspacing="0" border="0">
		<tr class="trtete">
		</tr>
		<tr style="display: none">
			<af:field styleClassLabel="td1"
				key="project_creation.field.project_location" property="location"
				value="" size="60" />
		</tr>
		<squale:iteratePaths name="clearCaseForm"
			key="project_creation.field.project_location" property="location"
			isRequired="true" disabled="<%=disabled%>" />
	</table>
	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<af:buttonBar>
			<af:button name="add.vob" toolTipKey="toolTip.add.vob"
				onclick="addField('vobsTable', 1);" singleSend="false" />
		</af:buttonBar>
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>
</af:form>