<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- Readers can't update the configuration --%>
<bean:define id="userProfile"
	name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
	property='<%="profile("+applicationId+")"%>' />
<%-- All the fields --%>
<%boolean disabled = false; // Used to grant writing %>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true;%>
</logic:equal>

<br />
<br />
<%-- General settings of Scm --%>
<af:form action="add_project_scm_config.do">
	<%-- projectId attribute to access rights --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">

	<p><bean:message key="project_creation.sourcemanager.help" /></p>
	<br/>	
	
	<table width="100%" class=formulaire cellpadding="0" cellspacing="0"
		border="0">

		<%-- Path to audit --%>
		<tr class="fondClair">
			<af:field key="project_creation.field.pathToAudit" name="scmForm"
				property="pathToAudit" isRequired="true" styleClassLabel="td1"
				size="100" disabled="<%=disabled%>" />
		</tr>			
		<%-- Login --%>
		<tr class="fondClair">
			<af:field key="project_creation.field.login" name="scmForm"
				property="login" isRequired="false" styleClassLabel="td1" size="60"
				disabled="<%=disabled%>" />
		</tr>
		<%-- Password --%>
		<tr>
			<af:field key="project_creation.field.password" name="scmForm" type="password"
				property="password" isRequired="false" styleClassLabel="td1" size="60"
				disabled="<%=disabled%>" />
		</tr>		
	</table>

	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>
</af:form>