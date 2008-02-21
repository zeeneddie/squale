<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>
<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants" %>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="userProfile"
	name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
	property="<%=\"profile(\"+applicationId+\")\"%>" />
<%-- Pour les champs --%>
<%boolean disabled = false;%>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true;%>
</logic:equal>
<br />
<af:form action="add_project_checkstyle_config.do">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<%-- Configuration du paramètre version du fichier de configuration checkstyle --%>
	<table width="100%" class="tblh" cellpadding="0" cellspacing="0"
		border="0">
		<tr>
			<td class="td1"><bean:message
				key="project_creation.checkstyle.field.conf_version" />*</td>
			<td align="left"><af:select isRequired="true"
				property="selectedRuleSet" disabled="<%=disabled%>">
				<af:option key="checkstyleVersions" value="" />
				<logic:iterate id="checkstyleVersions" name="checkstyleForm"
					property="versions" type="String">
					<af:option key="checkstyleVersions" value="<%=checkstyleVersions%>" />
				</logic:iterate>
			</af:select></td>
		</tr>
	</table>
	<input type="hidden" name="projectId" value="<%=projectId%>">
	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>
</af:form>