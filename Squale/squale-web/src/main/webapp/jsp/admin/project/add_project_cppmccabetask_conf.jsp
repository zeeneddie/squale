<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants" %>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<sec:notHasProfile var="disabled" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />

<br />
<bean:message key="project_creation.cpptest.details"/>
<af:form action="add_project_cpp_mccabe_config.do" method="POST">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<br />
	<table id="scriptTable" width="100%" class="tblh" cellpadding="0"
		cellspacing="0" border="0">
		<tr>
			<af:field key="project.parameter.compiling.cpp.scriptfile"
				property="cppScript" size="60" isRequired="true"
				disabled="<%=disabled%>" styleClassLabel="td1"/>
		</tr>
		<tr>
			<td class="td1"><bean:message key="project_creation.cpp.dialect" />*
			</td>
			<td><af:select isRequired="true" property="dialect" disabled="<%=disabled%>">
				<af:option key="empty" value="" />
				<af:option key="project.profile.cpp_sunws5x"
					value="<%=ParametersConstants.SUNWS_5X%>">
				</af:option>
				<af:option key="project.profile.cpp_forte"
					value="<%=ParametersConstants.FORTE%>">
				</af:option>
			</af:select></td>
		</tr>
	</table>
	<br />
	<br />
	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</sec:ifHasProfile>

</af:form>