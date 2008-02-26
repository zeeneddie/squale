<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="userProfile"
	name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
	property="<%=\"profile(\"+applicationId+\")\"%>" />
<%-- Pour les champs --%>
<%boolean disabled = false; // Les champs seront en lecture/écriture par défaut %>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true; // Pour indiquer la lecture seule des champs car l'utilisateur est en lecture seule%>
</logic:equal>

<br />
<bean:message key="project_creation.cpp.mccabe.details" />
<%-- Configuration des paramètres généraux de cpptest --%>
<af:form action="add_project_cpptest_config.do">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
		border="0">

		<%-- règles CppTest --%>
		<tr class="fondClair">
			<td class="td1"><bean:message
				key="project_creation.cpptest.field.ruleset" />*</td>
			<td><af:select property="selectedRuleSet" lazyLoading="false"
				isRequired="true" disabled="<%=disabled%>">
				<af:option key="cppconfig" value="" />
				<logic:iterate id="cppconfig" name="cppTestForm" property="ruleSets"
					scope="session" type="String">
					<af:option key="cppconfig" value="<%=cppconfig%>" />
				</logic:iterate>
			</af:select></td>
		</tr>
		<tr>
			<af:field key="project_creation.cpptest.field.script_location"
				property="script" size="60" isRequired="true" styleClassLabel="td1"
				disabled="<%=disabled%>" />
		</tr>
	</table>

	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>
</af:form>