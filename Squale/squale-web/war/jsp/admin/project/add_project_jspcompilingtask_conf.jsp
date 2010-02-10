<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>
<%-- Configuration de la compilation des JSP --%>
<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<sec:notHasProfile var="disabledObj" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />
<%boolean disabled = ((Boolean)pageContext.getAttribute("disabledObj")).booleanValue();%>

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
				property="webAppPath" size="60" disabled="<%=disabled%>" />
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
	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<af:buttonBar>
			<af:button onclick="addField('excludedJspDirTable', 3);"
				name="add_ex_compil_line" singleSend="false" />
		</af:buttonBar>
		<jsp:include page="common_parameters_buttons.jsp" />
	</sec:ifHasProfile>
</af:form>