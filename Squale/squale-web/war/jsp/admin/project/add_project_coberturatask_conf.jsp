<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"	prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<%-- 
	This JSP configures the generic task. A task configuration JSP is called if
	the task is present in the squalix_config.xml
--%>

<%-- Getting values for local beans : the project and the application ones --%>
<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- Getting the property of userProfile variable so as to define the authorisation --%>
<sec:notHasProfile var="disabledObj" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />
<%boolean disabled = ((Boolean)pageContext.getAttribute("disabledObj")).booleanValue();%>

<%-- 
	Main configuration of the generic tool thanks to a form :
	calling action addGenericTaskConfig in the struts-config.xml
--%>
<af:form action="add_project_coberturaTask_config.do">

	<%-- Defining a hidden input for the "projectId" --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">

	<%-- Printing some info messages for the user --%>
	<p><bean:message key="genericTask.inform.user.intro" /></p>
	<ul>
		<li><bean:message key="genericTask.inform.user.toolDir" /></li>
		<li><bean:message key="genericTask.inform.user.args_params" /></li>
		<li><bean:message key="genericTask.inform.user.workingDir" /></li>
		<li><bean:message key="genericTask.inform.user.resultsDir" /></li>
	</ul>

	<%--
		Creating a table containing the fields to display and/or edit the task configuration. Please notice that
		for the fields that are mandatory (if isRequired=true) one must verify in the Form that the fields are filled
		see GenericTaskForm.java for more info.  
	--%>
	<table id="genericTaskTable" width="100%" class=formulaire cellpadding="0" cellspacing="0" border="0">
		<thead>
			<tr><th><bean:message key="genericTask.conf.table.title" /></th><th></th></tr>
		</thead>

		<%-- Field for command that has to be executed --%>
		<tr>
			<af:field key="genericTask.conf.toolLocation" name="coberturaTaskForm"
				property="toolLocation" isRequired="false" styleClassLabel="td1" size="60"
				disabled="<%=disabled%>" />
		</tr>
		
		<%-- Field for the working directory --%>
		<tr>
			<af:field key="genericTask.conf.workingDir" name="coberturaTaskForm"
				property="workingDirectory" isRequired="false" styleClassLabel="td1" size="60"
				disabled="<%=disabled%>" />
		</tr>

		<%-- Field for commands and parameters --%>
		<tr>
			<af:field key="genericTask.conf.commands" type="textarea" name="coberturaTaskForm"
				property="commands" isRequired="false" styleClassLabel="td1" size="57"
				disabled="<%=disabled%>" />
		</tr>

		<%-- Field(s) for the result files/results location --%>
		
		<%-- This one is not displayed --%>
		<tr style="display:none">
			<af:field key="genericTask.conf.resultsLocation" property="resultsLocation" 
			styleClassLabel="td1" value="" size="60" />
		</tr>
		
		
		<%-- Whereas this one is displayed --%>
		<squale:iteratePaths name="coberturaTaskForm" property="resultsLocation" isRequired="true" 
			key="genericTask.conf.resultsLocation" disabled="<%=disabled%>" />
	</table>
	
	<%-- If the user is an administrator --%>
	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
	<%-- Including a validation button bar --%>
		<af:buttonBar>
			<!-- Specific button that add a field for another potential result locations -->
			<af:button onclick="addField('genericTaskTable',4);" name="add_resultLocation" singleSend="true" />
		</af:buttonBar>
		<!-- Including the standard button bar -->
		<jsp:include page="common_parameters_buttons.jsp" />
	</sec:ifHasProfile>

</af:form>
