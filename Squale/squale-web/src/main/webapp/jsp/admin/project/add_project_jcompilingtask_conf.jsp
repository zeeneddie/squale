<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page
	import="com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.LogonBean"%>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.JCompilingForm"%>
<%@ page
	import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO"%>

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
<%
// On indique que les champs sont en read/write par défaut
boolean disabled = false;
%>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true; //L'utilisateur n'a que les droits de lecture sur la page %>
</logic:equal>

<%-- Pour les paramètres eclipse --%>
<bean:define id="eclipseCompilation" name="jCompilingForm"
	property="eclipseCompilation" type="Boolean" />
<%
	// Affichage ou non des paramètres spécifiques à la compilation avec le plugin eclipse
	String displayEclipseParams = "display: none";
	if(eclipseCompilation.equals(Boolean.TRUE)) {
		displayEclipseParams = "display: block";
	}
%>

<%-- Configuration des paramètres généraux de compilation java d'un projet --%>
<br />
<bean:message key="project_creation.compiling.java.details" />
<br />
<br />


<%
// On récupère le type de la tâche
String kindOfTask = (String) request.getParameter("kindOfTask");
// Code défensif dans la cas où l'attibut serait nul
kindOfTask = (kindOfTask != null) ? kindOfTask : "";%>
<bean:define id="kindOfTaskId" value="<%=kindOfTask%>" />

<%-- Ajout d'une variable eclipse --%>
<logic:equal name="kindOfTaskId"
	value="<%=ParametersConstants.ECLIPSE_VARS%>">
	<af:form action="create_eclipse_var.do?addParameters">
		<%-- attribut projectId pour les droits d'accès --%>
		<input name="projectId" value="<%=projectId%>" type="hidden">
		<%-- type de la tâche --%>
		<input name="kindOfTask" value="<%=kindOfTask%>" type="hidden"
			id="kindOfTaskInputId" />
		<table width="100%" class="formulaire">
			<THEAD>
				<tr>
					<bean:message
						key="project_creation.compiling.java.eclipse.variable.creation" />
				</tr>
			</THEAD>
			<tr>
				<af:field key="project_creation.java.eclipse.var.creation.name"
					size="20" property="name" styleClassLabel="td1" isRequired="true" />
			</tr>
			<tr class="fondClair">
				<af:field key="project_creation.java.eclipse.var.creation.lib"
					property="lib" styleClassLabel="td1" size="90" isRequired="true" />
			<tr />
		</table>
		<af:buttonBar>
			<af:button name="valider" singleSend="true"
				callMethod="addEclipseVar" />
		</af:buttonBar>
	</af:form>
</logic:equal>

<%-- Ajout d'une librairie eclipse --%>
<logic:equal name="kindOfTaskId"
	value="<%=ParametersConstants.ECLIPSE_LIBS%>">
	<af:form action="create_eclipse_lib.do?addParameters">
		<%-- attribut projectId pour les droits d'accès --%>
		<input name="projectId" value="<%=projectId%>" type="hidden">
		<%-- type de la tâche --%>
		<input name="kindOfTask" value="<%=kindOfTask%>" type="hidden"
			id="kindOfTaskInputId" />
		<table width="100%" class="formulaire" id="eclipseLibTable">
			<THEAD>
				<tr>
					<bean:message
						key="project_creation.compiling.java.eclipse.lib.creation" />
				</tr>
			</THEAD>
			<tr>
				<af:field key="project_creation.java.eclipse.lib.creation.name"
					size="20" property="name" styleClassLabel="td1" isRequired="true" />
			</tr>
			<tr style="display: none">
				<af:field styleClassLabel="td1"
					key="project_creation.java.eclipse.lib.creation.lib"
					property="libs" value="" size="60" />
			</tr>
			<squale:iteratePaths name="eclipseUserLibForm"
				key="project_creation.java.eclipse.lib.creation.lib" property="libs"
				disabled="<%=disabled%>" isRequired="true" />
		</table>
		<af:buttonBar>
			<af:button onclick="addField('eclipseLibTable', 2);"
				name="add_lib_path_line" singleSend="false" />
			<af:button name="valider" singleSend="true"
				callMethod="addEclipseLib" />
		</af:buttonBar>
	</af:form>
</logic:equal>

<%-- on récupère un fichier ANT et/ou un workspace eclipse --%>
<af:form action="create_project_java_compiling.do?addParameters">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<%-- Insertion d'un fichier ANT --%>
	<input name="kindOfTask" value="<%=kindOfTask%>" type="hidden"
		id="kindOfTaskInputId" />
	<logic:equal name="kindOfTaskId" value="<%=ParametersConstants.ANT%>">
		<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
			border="0">
			<tr>
				<af:field key="project_creation.compiling.ant.srclocation" size="80"
					property="antFile" styleClassLabel="td1" isRequired="true" />
			</tr>
			<tr class="fondClair">
				<af:field key="project_creation.compiling.ant.target"
					property="antTaskName" styleClassLabel="td1" size="60" />
			<tr />
		</table>
		<af:buttonBar>
			<af:button name="add.compiling.configuration"
				callMethod="addProjectJavaCompiling" singleSend="true" />
		</af:buttonBar>
	</logic:equal>

	<%-- Insertion d'un worspace WSAD --%>
	<logic:equal name="kindOfTaskId" value="<%=ParametersConstants.WSAD%>">
		<br />
		<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
			border="0">
			<tr>
				<af:field key="project_creation.compiling.workspace.location"
					size="80" property="workspacePath" styleClassLabel="td1"
					isRequired="true" />
			<tr />
				<%-- Manifest dans le cas de la compilation RCP par exemple --%>
			<tr>
				<af:field key="project_creation.compiling.workspace.manifest"
					property="manifestPath" styleClassLabel="td1" size="80" />
			<tr />
		</table>
		<af:buttonBar>
			<af:button name="add.compiling.configuration"
				callMethod="addProjectJavaCompiling" singleSend="true" />
		</af:buttonBar>
	</logic:equal>

	<%-- Insertion d'un worspace RSA --%>
	<logic:equal name="kindOfTaskId" value="<%=ParametersConstants.RSA%>">
		<br />
		<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
			border="0">
			<tr>
				<af:field key="project_creation.compiling.workspace.location"
					size="80" property="workspacePath" styleClassLabel="td1"
					isRequired="true" />
			<tr />
			<tr class="fondClair">
				<af:field key="project_creation.compiling.workspace.ear"
					property="earName" styleClassLabel="td1" />
			<tr />
			<tr>
				<af:field key="project_creation.compiling.workspace.manifest"
					property="manifestPath" styleClassLabel="td1" size="80" />
			<tr />
		</table>
		<af:buttonBar>
			<af:button name="add.compiling.configuration"
				callMethod="addProjectJavaCompiling" singleSend="true" />
		</af:buttonBar>
	</logic:equal>
</af:form>

<%-- Affichage du tableau affichant l'ordre de lancement dans la compilation --%>
<h5><bean:message key="project_creation.compiling.summary.title" /></h5>
<div style="color: #f00"><html:messages property="ruleSelection"
	id="message" message="true">
	<bean:write name="message" />
</html:messages></div>
<af:form action="create_project_list_compiling.do">
	<%-- Définit le type de compilation Java --%>
	<input name="kindOfTask" id="kOT" type="hidden" value="" />
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<bean:size id="nbOfRule" name="jCompilingForm"
		property="compilationRules" />
	<af:field key="project_creation.compiling.rule.mandatory"
		property="compilationRules" isRequired="true"
		value="<%=\"\"+nbOfRule.intValue()%>" disabled="true" size="1" />
	<af:table name="jCompilingForm" property="compilationRules"
		displayNavigation="none" emptyKey="table.results.none"
		displayFooter="false">
		<logic:greaterThan name="nbOfRule" value="0">
			<af:cols id="rule" selectable="<%=!disabled%>">
				<af:col property="kindOfTask" key="project_creation.compiling.type" />
				<af:col property=""
					key="project_creation.compiling.file_or_workspace">
					<logic:equal name="rule" property="kindOfTask"
						value="<%=ParametersConstants.ANT%>">
						<bean:define id="antProject" value="true" />
						<bean:write name="rule" property="antFile" />
					</logic:equal>
					<logic:equal name="rule" property="kindOfTask"
						value="<%=ParametersConstants.WSAD%>">
						<bean:write name="rule" property="workspacePath" />
					</logic:equal>
					<logic:equal name="rule" property="kindOfTask"
						value="<%=ParametersConstants.RSA%>">
						<bean:write name="rule" property="workspacePath" />
					</logic:equal>
				</af:col>
				<af:col property="" key="project_creation.compiling.task_or_project">
					<logic:equal name="rule" property="kindOfTask"
						value="<%=ParametersConstants.ANT%>">
						<bean:write name="rule" property="antTaskName" />
					</logic:equal>
					<logic:equal name="rule" property="kindOfTask"
						value="<%=ParametersConstants.WSAD%>">
						<bean:write name="createProjectForm" property="projectName"
							scope="session" />
					</logic:equal>
					<logic:equal name="rule" property="kindOfTask"
						value="<%=ParametersConstants.RSA%>">
						<bean:write name="createProjectForm" property="projectName"
							scope="session" />
					</logic:equal>
				</af:col>
				<logic:equal name="rule" property="kindOfTask"
					value="<%=ParametersConstants.WSAD%>">
					<af:col property="" key="project_creation.compiling.manifest">
						<bean:write name="rule" property="manifestPath" />
					</af:col>
				</logic:equal>
				<logic:equal name="rule" property="kindOfTask"
					value="<%=ParametersConstants.RSA%>">
					<af:col property="" key="project_creation.compiling.earName">
						<bean:write name="rule" property="earName" />
					</af:col>
					<af:col property="" key="project_creation.compiling.manifest">
						<bean:write name="rule" property="manifestPath" />
					</af:col>
				</logic:equal>
			</af:cols>
		</logic:greaterThan>

	</af:table>
	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<af:buttonBar>
			<logic:equal name="jCompilingForm" property="antRulesAvailable"
				value="true">
				<af:button name="add.compiling.java.ant" singleSend="true"
					toolTipKey="buttonTag.form.add.compiling.java.ant"
					onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+ParametersConstants.ANT+\"');\"%>" />
			</logic:equal>
			<logic:equal name="jCompilingForm" property="wsadRulesAvailable"
				value="true">
				<af:button name="add.compiling.java.wsad.project" singleSend="true"
					toolTipKey="buttonTag.form.add.compiling.java.wsad.project"
					onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+ParametersConstants.WSAD+\"');\"%>" />
			</logic:equal>
			<logic:equal name="jCompilingForm" property="rsaRulesAvailable"
				value="true">
				<af:button name="add.compiling.java.rsa.project" singleSend="true"
					toolTipKey="buttonTag.form.add.compiling.java.rsa.project"
					onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+ParametersConstants.RSA+\"');\"%>" />
			</logic:equal>
			<bean:size id="nbRules" name="jCompilingForm"
				property="compilationRules" />
			<logic:greaterThan name="nbRules" value="0">
				<af:button name="delete" singleSend="true"
					onclick="submitWithAction('jCompilingForm', 'removeCompilingRules', '');"
					type="form" />
			</logic:greaterThan>
		</af:buttonBar>
	</logic:notEqual>

	<br />
	<table id="excludedDirTable" width="100%" class="tblh" cellpadding="0"
		cellspacing="0" border="0">
		<THEAD>
			<tr>
				<bean:message
					key="project_creation.compiling.java.general.parameters" />
			</tr>
		</THEAD>
		<tr>
			<td class="td1"><bean:message
				key="project_creation.java.version" />*</td>
			<td><af:select isRequired="true" property="dialect"
				disabled="<%=disabled%>">
				<af:option key="project.profile.java.1_3"
					value="<%=ParametersConstants.JAVA1_3%>">
				</af:option>
				<af:option key="project.profile.java.1_4"
					value="<%=ParametersConstants.JAVA1_4%>">
				</af:option>
				<af:option key="project.profile.java.1_5"
					value="<%=ParametersConstants.JAVA1_5%>">
				</af:option>
			</af:select></td>
		</tr>
		<tr style="display: none">
			<af:field styleClassLabel="td1"
				key="project_creation.exclude.compilation.title"
				property="excludeDirectories" value="" size="60" />
		</tr>
		<squale:iteratePaths name="jCompilingForm"
			key="project_creation.exclude.compilation.title"
			property="excludeDirectories" disabled="<%=disabled%>" />
	</table>
	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<af:buttonBar>
			<af:button onclick="addField('excludedDirTable', 2);"
				name="add_ex_compil_line" singleSend="false" />
		</af:buttonBar>
	</logic:notEqual>
	<br />
	<logic:empty name="antProject">
		<h3><bean:message
			key="project_creation.compiling.java.eclipse.parameters" /></h3>
		<br />
		<af:dropDownPanel titleKey="buttonTag.menu.aide">
			<bean:message key="project_creation.java.bundle_path.help" />
			<br />
			<br />
		</af:dropDownPanel>
		<table id="eclipseTable" width="100%" class="tblh" cellpadding="0"
			cellspacing="0" border="0">
			<THEAD>
				<tr>
					<bean:message key="project_creation.compiling.java.eclipse.version" />
				</tr>
			</THEAD>
			<bean:define id="needBundle" name="jCompilingForm"
				property="needBundle" type="Boolean" />
			<bean:define id="bundlePath" name="jCompilingForm"
				property="bundlePath" type="String" />
			<%-- Liste proposant les différentes version d'éclipse dans le cas d'une compilation RCP --%>
			<%
	// On récupère la clé représentant la liste des chemins vers les versions d'eclipse
	String eclipseStr = WebMessages.getString(request, "eclipseRCPList");
	if (null == eclipseStr) {
	    eclipseStr = ""; // code défensif
	}
	// On modifie l'attribut "needBundle" dans le cas où le apparaît dans la liste
	needBundle = new Boolean(!eclipseStr.matches(".*" + bundlePath + ".*"));
	String displayBundlePath = "display: none";
	if(needBundle.equals(Boolean.TRUE)) {
		displayBundlePath = "display: block";
	}
	// On le change dans le formulaire
	 ((JCompilingForm) session.getAttribute("jCompilingForm")).setNeedBundle(needBundle.booleanValue());
	if (!needBundle.booleanValue()) {
	    bundlePath = "";
	    ((JCompilingForm) session.getAttribute("jCompilingForm")).setBundlePath(bundlePath);
	}
	// La chaîne récupérée ne doit pas être nulle est doit être de la forme path1,path2
	String[] eclipseVersions = new String[0];
	if (null != eclipseStr) {
	    eclipseVersions = eclipseStr.split(",");
	}
	// On rend le tableau accessible
	pageContext.setAttribute("eclipseVersions", eclipseVersions);%>
			<tr>
				<td class="td1"><bean:message
					key="project_creation.java.eclipse.version" />*</td>
				<td><af:select property="bundlePathDefault"
					disabled="<%=disabled%>">
					<logic:iterate name="eclipseVersions" id="eclipseVersion"
						type="String">
						<%
	// Pour chaque chemin on récupère le nom du répertoire ou fichier sans l'extension
	String fileName = eclipseVersion.trim().replace('\\', '/'); // Pour uniformiser les chemins
	// On supprime les séparateurs de fichiers en fin de nom
	fileName = fileName.replaceFirst("[/\\\\]+$", "");
	int lastSlash = fileName.lastIndexOf("/");
	if (lastSlash > 0) {
	    // On récupère le nom du fichier et on le met en majuscule pour uniformiser la typographie
	    fileName = fileName.substring(lastSlash + 1).toUpperCase();
	}
	int lastDot = fileName.lastIndexOf(".");
	if (lastDot > 0) {
	    // On supprime l'extension du fichier
	    fileName = fileName.substring(0, lastDot);
	}%>
						<af:option value="<%=eclipseVersion.trim()%>">
							<%=fileName%>
						</af:option>
					</logic:iterate>
				</af:select></td>
			</tr>
			<%-- Checkbox permettant d'afficher ou de masquer le paramètre du chemin vers le bundle --%>
			<tr>
				<af:field type="checkbox"
					key="project_creation.java.need.bundle_path"
					onclick="displayField(this, 'bundlePathField')"
					property="needBundle" disabled="<%=disabled%>" />
			</tr>
			<%-- Chemin vers le bundle eclipse si nécessaire --%>
			<tr style="<%=displayBundlePath%>" id="bundlePathField">
				<af:field styleClassLabel="td1"
					key="project_creation.java.bundle_path" property="bundlePath"
					size="60" disabled="<%=disabled%>" />
			</tr>
		</table>
		<br />

		<%-- Les paramètres eclipse --%>

		<%-- checkbox indiquant que le projet doit être compilé avec le plugin eclipse --%>
		<logic:equal name="userProfile"
			value="<%=ProfileBO.ADMIN_PROFILE_NAME%>">
			<af:field type="checkbox" property="eclipseCompilation"
				accessKey="admin" key="project_creation.java.eclipse.compilation"
				onclick="displayField(this, 'eclipseDiv')" />
		</logic:equal>
		<br />
		<br />
		<%-- Configuration eclipse --%>
		<div id="eclipseDiv" style="<%=displayEclipseParams%>">
		<h3><bean:message
			key="project_creation.compiling.java.eclipse.variable.title" /></h3>
		<%-- variables eclipse --%> <af:dropDownPanel
			titleKey="buttonTag.menu.aide">
			<bean:message
				key="project_creation.compiling.java.eclipse.variable.help" />
			<br />
			<br />
		</af:dropDownPanel> <af:table name="jCompilingForm" property="eclipseVars"
			totalLabelKey="empty" emptyKey="table.results.none">
			<af:cols id="variable" idIndex="index">
				<af:colSelect />
				<af:col property="name" key="project_creation.java.eclipse.var.name" />
				<af:col property="lib" key="project_creation.java.eclipse.var.lib" />
			</af:cols>
			<logic:notEqual name="userProfile"
				value="<%=ProfileBO.READER_PROFILE_NAME%>">
				<af:buttonBar>
					<af:button
						onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+ParametersConstants.ECLIPSE_VARS+\"');\"%>"
						name="add_eclipse_var" singleSend="true" />
					<bean:size id="nbVars" name="jCompilingForm" property="eclipseVars" />
					<logic:greaterThan name="nbVars" value="0">
						<af:button name="delete" singleSend="true"
							onclick="submitWithAction('jCompilingForm', 'removeEclipseVars', 'nothing');"
							type="form" />
					</logic:greaterThan>
				</af:buttonBar>
			</logic:notEqual>
		</af:table> <br />
		<h3><bean:message
			key="project_creation.compiling.java.eclipse.lib.title" /></h3>

		<%-- librairies eclipse --%> <af:dropDownPanel
			titleKey="buttonTag.menu.aide">
			<bean:message key="project_creation.compiling.java.eclipse.lib.help" />
			<br />
			<br />
		</af:dropDownPanel> <af:table name="jCompilingForm" property="eclipseLibs"
			totalLabelKey="empty" emptyKey="table.results.none">
			<af:cols id="lib" idIndex="index">
				<af:colSelect />
				<af:col property="name" key="project_creation.java.eclipse.lib.name" />
				<af:col property="libsStr"
					key="project_creation.java.eclipse.lib.libs" />
			</af:cols>
			<logic:notEqual name="userProfile"
				value="<%=ProfileBO.READER_PROFILE_NAME%>">
				<af:buttonBar>
					<af:button
						onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+ParametersConstants.ECLIPSE_LIBS+\"');\"%>"
						name="add_eclipse_lib" singleSend="true" />
					<bean:size id="nbLibs" name="jCompilingForm" property="eclipseLibs" />
					<logic:greaterThan name="nbLibs" value="0">
						<af:button name="delete" singleSend="true"
							onclick="submitWithAction('jCompilingForm', 'removeEclipseLibs', 'nothing');"
							type="form" />
					</logic:greaterThan>
				</af:buttonBar>
			</logic:notEqual>
		</af:table> <br />
		<%-- Advanced options visible only by administrators --%> <logic:equal
			name="userProfile" value="<%=ProfileBO.ADMIN_PROFILE_NAME%>">
			<h3><bean:message
				key="project_creation.compiling.java.eclipse.advanced_options.title" /></h3>
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message
					key="project_creation.java.eclipse.advanced_options.help"
					arg0='<%=WebMessages.getString(request, "eclipse.compilation.advanced.options")%>' />
			</af:dropDownPanel>
			<br />
			<br />
			<table id="advancedOptionsTable" class="formulaire">
				<THEAD>
					<tr>
						<bean:message key="project_creation.java.eclipse.advanced_options" />
					</tr>
				</THEAD>
				<TBODY>
					<tr>
						<af:field name="jCompilingForm" property="advancedOptions"
							key="project_creation.java.eclipse.advanced_options" size="90" />
					</tr>
				</TBODY>
			</table>
			<br />
			<br />
		</logic:equal></div>
	</logic:empty>
	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>
</af:form>