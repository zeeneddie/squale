<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<script type="text/javascript" src="/squale/jslib/format_page.js"></script>
<%-- Configuration de la compilation des JSP --%>
<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="user"
	name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>" />
<bean:define id="userProfile" name="user"
	property="<%=\"profile(\"+applicationId+\")\"%>" />
<%-- Pour les champs --%>
<%boolean disabled = false; // Les champs seront en lecture/écriture par défaut%>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true; // Pour indiquer la lecture seule des champs car l'utilisateur est en lecture seule%>
</logic:equal>

<br />
<bean:message key="project_creation.compiling.jsp.details" />
<br />
<br />

<af:form action="add_project_jsp_compiling_parameters.do">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<table id="excludedJspDirTable" width="100%" class="tblh"
		cellpadding="0" cellspacing="0" border="0">
		<THEAD>
			<tr>
				<th></th>
				<th style="width: 50%"><bean:message
					key="project_creation.compiling.java.general.parameters" /></th>
			</tr>
		</THEAD>
		<tr>
			<af:field styleClassLabel="td1" isRequired="true"
				key="project_creation.jsp.web_app_path"
				property="webAppPath" size="60" />
		</tr>
		<tr>
			<td class="td1"><bean:message key="project_creation.jsp.j2ee_version" />*
			</td>
			<td><af:select isRequired="true" property="j2eeVersion"
				disabled="<%=disabled%>">
				<af:option key="empty" value="">
				</af:option>
				<af:options name="jspCompilingForm" property="j2eeVersions" />
			</af:select></td>
		</tr>
		<tr style="display: none">
			<af:field styleClassLabel="td1"
				key="project_creation.exclude.compilation.title"
				property="excludeJspDir" value="" size="60" />
		</tr>
		<squale:iteratePaths name="jspCompilingForm"
			key="project_creation.exclude.compilation.title"
			property="excludeJspDir" disabled="<%=disabled%>" />
	</table>
	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<af:buttonBar>
			<af:button onclick="addField('excludedJspDirTable', 3);"
				name="add_ex_compil_line" singleSend="false" />
		</af:buttonBar>
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>
</af:form>