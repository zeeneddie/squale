<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- Readers can't update the configuration --%>
<sec:notHasProfile var="disabledObj" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />
<%boolean disabled = ((Boolean)pageContext.getAttribute("disabledObj")).booleanValue();%>

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
	
	<%-- Path to audit --%>	
	<table id="locationsTable" width="100%" class="formulaire" cellpadding="0"
		 border="0">
		<tr class="trtete">
		</tr>		 
		<tr style="display: none">
			<af:field styleClassLabel="td1"
				key="project_creation.field.pathToAudit" property="location"
				value="" size="60" />
		</tr>
		<squale:iteratePaths name="scmForm"
			key="project_creation.field.pathToAudit" property="location"
			isRequired="true" disabled="<%=disabled%>" />
	</table>	

	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<af:buttonBar>
			<af:button name="add.scmPath" toolTipKey="toolTip.add.vob"
				onclick="addField('locationsTable', 1);" singleSend="false" />
		</af:buttonBar>	
		<jsp:include page="common_parameters_buttons.jsp" />
	</sec:ifHasProfile>
</af:form>