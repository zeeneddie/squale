<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants"%>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.LogonBean"%>
<%@ page import="org.squale.squaleweb.resources.WebMessages"%>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.component.parameters.JCompilingForm"%>
<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO"%>

<bean:define id="projectId" name="createProjectForm" property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm" property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="user" name="<%=org.squale.welcom.struts.util.WConstants.USER_KEY%>" />
<bean:define id="userProfile" name="user" property="<%=\"profile(\"+applicationId+\")\"%>" />

<%-- Pour les champs --%>
<sec:notHasProfile var="disabledObj" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />
<%boolean disabled = ((Boolean)pageContext.getAttribute("disabledObj")).booleanValue();%>

<%-- Pour les paramètres eclipse --%>
<bean:define id="eclipseCompilation" name="jCompilingForm" property="eclipseCompilation" type="Boolean" />

<%
	// Affichage ou non des paramètres spécifiques à la compilation avec le plugin eclipse
	String displayEclipseParams = "display: none";
	if(eclipseCompilation.equals(Boolean.TRUE)) {
		displayEclipseParams = "display: block";
	}
%>

<bean:define id="kindOfCompilSaved" name="jCompilingForm" property="kindOfCompil" type="String"/>
<%
	// On récupère le type de la tâche
	String kindOfTask = (String) request.getParameter("kindOfTask");
	
	// Code défensif dans la cas où l'attibut serait nul
	kindOfTask = (kindOfTask != null) ? kindOfTask : "";
	
	String curCompil = kindOfCompilSaved;
	if ( curCompil.equals(ParametersConstants.NOT_DEFINED) && !kindOfTask.equals("") )
	{
	    curCompil=kindOfTask;
	}
	
	
	String rsa_wsad = ParametersConstants.RSA;
	if (curCompil.equals(ParametersConstants.WSAD))
	{
	    rsa_wsad=ParametersConstants.WSAD;
	}

%>
<bean:define id="kindOfTaskId" value="<%=kindOfTask%>" />
<bean:define id="currentKindOfCompil" value="<%= curCompil %>"/>

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

<%-- Choice the kind of compilation --%>
<%-- Displayed only if the kind of compilation hasn't been done --%>
<logic:equal name="currentKindOfCompil" value="<%=ParametersConstants.NOT_DEFINED%>">
	<%-- This block is not displayed if the user is only a reader for the application --%>	
	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<af:form action="create_project_list_compiling.do">	
			<br/>
			<bean:message key="project_creation.compiling.java.details" />
			<br/>
			<input name="kindOfTask" id="kOT" type="hidden" value="" />
			<input name="projectId" value="<%=projectId%>" type="hidden">
			<af:buttonBar>
					<%-- Button to do an ant compilation --%>
					<af:button name="compiling.java.ant" singleSend="true"
							toolTipKey="buttonTag.form.compiling.java.ant"
							onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+ParametersConstants.ANT+\"');\"%>" />
					<%-- Button to do an eclipse compilation --%>
					<af:button name="compiling.java.eclipse" singleSend="true"
							toolTipKey="buttonTag.form.compiling.java.eclipse"
							onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+rsa_wsad+\"');\"%>" />
			</af:buttonBar>
		</af:form>
	</sec:ifHasProfile>
</logic:equal>

<%-- Bloc displayed when the kind of compilation has been chosen --%>
<logic:notEqual name="currentKindOfCompil" value="<%=ParametersConstants.NOT_DEFINED%>">
	<af:form action="create_project_java_compiling.do?addParameters">
		<%-- Display compilation information according to the kind of compilation --%>
		<logic:equal name="currentKindOfCompil" value="<%= ParametersConstants.ANT %>">
			<h4><bean:message key="project_creation.compiling.java.ant" /></h4>
			<bean:message key="project_creation.compiling.rule.java.mandatory"/>
			<bean:message key="project_creation.compiling.java.ant.details" />
		</logic:equal>
		<logic:equal name="currentKindOfCompil" value="<%= rsa_wsad %>">
			<h4><bean:message key="project_creation.compiling.java.eclipse"/></h4>
			<bean:message key="project_creation.compiling.rule.java.mandatory"/>
			<bean:message key="project_creation.compiling.java.eclipse.details" />
		</logic:equal>
		<br/>
		<br/>
		<%-- projectId attribute for access right --%>
		<input name="projectId" value="<%=projectId%>" type="hidden">
		
		
		<input name="kindOfTask" value="<%=kindOfTask%>" type="hidden" id="kindOfTaskInputId" />
		
		<%-- div style="padding-left:50px; padding-right:50px; "--%>
			<%-- Table for enter an ANT compilation rule --%>
			<logic:equal name="kindOfTaskId" value="<%=ParametersConstants.ANT%>">
				</br>
				<table class="formulaire" cellpadding="0" cellspacing="0"
					border="0">
					<thead>
						<tr>
							<th colspan="2">
								<bean:message key="project_creation.compiling.java.ant.addCompil"/>
							</th>
						</tr>
					</thead>
					<tbody>			
						<tr>
							<af:field key="project_creation.compiling.ant.srclocation" size="80"
								property="antFile" styleClassLabel="td1" isRequired="true" />
						</tr>
						<tr class="fondClair">
							<af:field key="project_creation.compiling.ant.target"
								property="antTaskName" styleClassLabel="td1" size="60" />
						<tr />
					</tbody>
				</table>
				<af:buttonBar>
					<af:button name="add.compiling.configuration"
						callMethod="addProjectJavaCompiling" singleSend="true" />
				</af:buttonBar>
				</br>
			</logic:equal>
		
			<%-- Table for insert an Eclipse compilation rule --%>
			<logic:equal name="kindOfTaskId" value="<%= rsa_wsad %>">
				</br>
				<table class="formulaire" cellpadding="0" cellspacing="0"
					border="0">
					<thead>
						<tr>
							<th colspan="3">
								<bean:message key="project_creation.compiling.java.eclipse.addCompil"/>
							</th>
						</tr>
					</thead>
					<tbody>		
						<tr>
							<af:field key="project_creation.compiling.workspace.location"
								size="80" property="workspacePath" styleClassLabel="td1"
								isRequired="true" />
						</tr>
						<logic:equal name="kindOfTaskId" value="<%=ParametersConstants.RSA %>">
							<tr class="fondClair" >
								<af:field key="project_creation.compiling.workspace.ear"
									property="earName" styleClassLabel="td1" />
							</tr>
						</logic:equal>
						<logic:equal name="kindOfTaskId" value="<%=ParametersConstants.WSAD %>">
							<tr class="fondClair" >
								<af:field key="project_creation.compiling.workspace.ear"
									property="earName" styleClassLabel="td1" disabled="true"/>
							</tr>
						</logic:equal>
						<tr>							
							<af:field key="project_creation.compiling.workspace.manifest"
								property="manifestPath" styleClassLabel="td1" size="80" />
						</tr >
					</tbody>
				</table>
				<af:buttonBar>
					<af:button name="add.compiling.configuration"
						callMethod="addProjectJavaCompiling" singleSend="true" />
				</af:buttonBar>
				</br>
			</logic:equal>
		<%-- /div --%>
	</af:form>
	<br/>

	<af:form action="create_project_list_compiling.do">
		<%-- Définit le type de compilation Java --%>
		<input name="kindOfTask" id="kOT" type="hidden" value="" />
		<%-- attribut projectId pour les droits d'accès --%>
		<input name="projectId" value="<%=projectId%>" type="hidden">
		
		<%-- General compilation settings --%>

		<h3>
			<bean:message key="project_creation.compiling.java.general.parameters" />
		</h3>

		<bean:size id="nbOfRule" name="jCompilingForm" property="compilationRules" />		

		<%-- Table of the rule of compilations --%>
		<%-- Displayed only if there is at least one rule --%>
		<logic:greaterThan name="nbOfRule" value="0">
	
			<br/>
			<div style="color: #f00">
				<html:messages property="ruleSelection" id="message" message="true">
					<bean:write name="message" />
				</html:messages>
			</div>
			<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
					border="0">
				<thead>
					<tr>
						<th><bean:message key="project_creation.compiling.summary.title" /></th>
					</tr>
				</thead>
			</table>
			<af:table name="jCompilingForm" property="compilationRules"
				displayNavigation="none" emptyKey="table.results.none"
				displayFooter="false">
				
					<%-- The first column is selectable.--%>
					<af:cols id="rule" selectable="<%=!disabled%>">
						
						<%-- Second column displays the kind of task--%>
						<%--<af:col property="kindOfTask" key="project_creation.compiling.type" />--%>
						
						<%-- Third column dispalys the path to the ANT file or the eclipse workspace  --%>
						<logic:equal name="rule" property="kindOfTask" value="<%=ParametersConstants.ANT%>">
							<af:col property="" key="project_creation.compiling.java.ant.filePath">
								<bean:define id="antProject" value="true" />
								<bean:write name="rule" property="antFile" />
							</af:col>
						</logic:equal>
						<logic:equal name="rule" property="kindOfTask" value="<%= rsa_wsad %>">
							<af:col property="" key="project_creation.compiling.java.eclipse.workspacePath">
								<bean:write name="rule" property="workspacePath" />
							</af:col>
						</logic:equal>
						
						<%-- The fourth column displays the target for ant, the ear name for eclipse --%>
						<logic:equal name="rule" property="kindOfTask" value="<%=ParametersConstants.ANT%>">
							<af:col property="" key="project_creation.compiling.java.ant.launchTarget">
								<bean:write name="rule" property="antTaskName" />
							</af:col>
						</logic:equal>
						<logic:equal name="rule" property="kindOfTask" value="<%= rsa_wsad %>">
							<af:col property="" key="project_creation.compiling.earName">
								<bean:write name="rule" property="earName" />
							</af:col>
						</logic:equal>

						<%-- The last column is only displays for eclipse project --%>
						<logic:equal name="rule" property="kindOfTask" value="<%= rsa_wsad %>" >

							<%-- The fifth column displays the path to the manifest  --%>
							<af:col property="" key="project_creation.compiling.manifest">
								<bean:write name="rule" property="manifestPath" />
							</af:col>
						</logic:equal>
					</af:cols>
			</af:table>
		
			<%-- the button for displays the add compilation rule table are displayed only if the user is 
			not a reader for the application --%>
			<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
				<af:buttonBar>

					<%-- Displays table for add an ANT compilation rule --%>
					<logic:equal name="currentKindOfCompil" value="<%=ParametersConstants.ANT %>">
						<af:button name="add.compiling.java.ant" singleSend="true"
							toolTipKey="buttonTag.form.add.compiling.java.ant"
							onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+ParametersConstants.ANT+\"');\"%>" />
					</logic:equal>

					<%-- Displays table for add an eclipse compilation rule --%>
					<logic:equal name="currentKindOfCompil" value="<%= rsa_wsad %>">
						<af:button name="add.compiling.java.rsa.project" singleSend="true"
							toolTipKey="buttonTag.form.add.compiling.java.rsa.project"
							onclick="<%=\"submitWithAction('jCompilingForm', 'addParameters', '\"+rsa_wsad+\"');\"%>" />
					</logic:equal>

					<%-- Displays the delete button rule--%>
					<af:button name="delete" singleSend="true"
						onclick="submitWithAction('jCompilingForm', 'removeCompilingRules', '');"
						type="form" />

				</af:buttonBar>
			</sec:ifHasProfile>
		</logic:greaterThan>
		<br />
	
		<%-- Java version selection table --%>
		<table id="javaVersion" width="100%" class="tblh" cellpadding="0"
			cellspacing="0" border="0">
			<thead>
				<tr>
					<td colspan="2">
						<bean:message key="project_creation.compiling.java.version" />
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="td1">
						<bean:message key="project_creation.java.version" />*
					</td>
					<td>
						<af:select isRequired="true" property="dialect" disabled="<%=disabled%>">
							<af:option key="project.profile.java.1_3" value="<%=ParametersConstants.JAVA1_3%>"/>
							<af:option key="project.profile.java.1_4" value="<%=ParametersConstants.JAVA1_4%>"/>
							<af:option key="project.profile.java.1_5" value="<%=ParametersConstants.JAVA1_5%>"/>
						</af:select>
					</td>
				</tr>
			</tbody>
		</table>
		</br>

		<%-- Compilation exclusion table --%>
		<table id="excludedDirTable" width="100%" class="tblh" cellpadding="0"
			cellspacing="0" border="0">
			<thead>
				<tr>
					<td colspan="2">
						<bean:message key="project_creation.exclude.compilation.title" />
					</td>
				</tr>
			</thead>
			<tbody>
				<tr style="display: none">
					<af:field styleClassLabel="td1"
						key="project_creation.exclude.compilation.title"
						property="excludeDirectories" value="" size="60" />
				</tr>
				<squale:iteratePaths name="jCompilingForm"
					key="project_creation.exclude.compilation.title"
					property="excludeDirectories" disabled="<%=disabled%>" />
			</tbody>
		</table>

		<%-- If the user is not a reader for the application, we display the add exclusion path button --%>
		<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
			<af:buttonBar>
				<af:button onclick="addField('excludedDirTable', 2);"
					name="add_ex_compil_line" singleSend="false" />
			</af:buttonBar>
		</sec:ifHasProfile>

		<%-- End General compilation settings --%>
	
		<br/>
		<br/>

		<%-- Eclipse parameters --%>
		<%-- this block is diplayed only for an eclipse compilation --%>
		<logic:equal name="currentKindOfCompil" value="<%= rsa_wsad %>">
			<div id="eclipseDiv">
				<%-- General eclipse setting --%>
				<h3>
					<bean:message key="project_creation.compiling.java.eclipse.parameters" />
				</h3>
				<af:dropDownPanel titleKey="buttonTag.menu.aide">
					<bean:message key="project_creation.java.bundle_path.help" />
					<br/>
				</af:dropDownPanel>
				<br/>
				<table id="eclipseTable" width="100%" class="tblh" cellpadding="0" cellspacing="0" border="0">
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="project_creation.compiling.java.eclipse.version" />
						</td>
					</tr>
				</thead>
				<bean:define id="needBundle" name="jCompilingForm" property="needBundle" type="Boolean" />
				<bean:define id="bundlePath" name="jCompilingForm" property="bundlePath" type="String" />
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
					pageContext.setAttribute("eclipseVersions", eclipseVersions);
				%>
				<tbody>

					<%-- Choice of an eclipse bundle --%>
					<tr>
						<td class="td1" >
							<bean:message key="project_creation.java.eclipse.version" />*
						</td>
						<td >
							<af:select property="bundlePathDefault" disabled="<%=disabled%>">
								<logic:iterate name="eclipseVersions" id="eclipseVersion" type="String">

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
									}
								%>
									<af:option value="<%=eclipseVersion.trim()%>">
										<%=fileName%>
									</af:option>
								</logic:iterate>
							</af:select>
						</td>
					</tr>
					
					<%-- Checkbox for a path to a bundle --%>
					<tr>
						<af:field type="checkbox" colspan="2"
							key="project_creation.java.need.bundle_path"
							onclick="displayField(this, 'bundlePathField')"
							property="needBundle" disabled="<%=disabled%>"  />
					</tr>
				
					<%-- Path to an eclips bundle --%>
					<tr style="<%=displayBundlePath%>" id="bundlePathField" >
						<af:field styleClassLabel="td1"
							key="project_creation.java.bundle_path" property="bundlePath"
							size="60" disabled="<%=disabled%>" />
					</tr>
				</tbody>
			</table>
			<br/>
			<%-- End Eclipse general parameters --%>
	
			<%-- Eclipse advanced use--%>
			<af:dropDownPanel titleKey="project_creation.compiling.java.eclipse.advancedUse" >
				<br/>
				<%-- Eclipse variables --%>
				<h3>
					<bean:message key="project_creation.compiling.java.eclipse.variable.title" />
				</h3>
				<af:dropDownPanel titleKey="buttonTag.menu.aide">
					<bean:message key="project_creation.compiling.java.eclipse.variable.help" />
					<br />
					<br />
				</af:dropDownPanel>
				<af:table name="jCompilingForm" property="eclipseVars" 
					totalLabelKey="empty" emptyKey="table.results.none">
					<af:cols id="variable" idIndex="index" >
						<af:colSelect />
						<af:col property="name" key="project_creation.java.eclipse.var.name" />
						<af:col property="lib" key="project_creation.java.eclipse.var.lib" />
					</af:cols>
					<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
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
					</sec:ifHasProfile>
				</af:table>	
			<%-- End eclipse variables--%>
				<br/>
				<br/>

			<%-- Eclipse libraries --%>
				<h3>
					<bean:message key="project_creation.compiling.java.eclipse.lib.title" />
				</h3>
			 	<af:dropDownPanel titleKey="buttonTag.menu.aide">
					<bean:message key="project_creation.compiling.java.eclipse.lib.help" />
				</af:dropDownPanel>		
				<af:table name="jCompilingForm" property="eclipseLibs"
					totalLabelKey="empty" emptyKey="table.results.none" >
					<af:cols id="lib" idIndex="index">
						<af:colSelect />
						<af:col property="name" key="project_creation.java.eclipse.lib.name" />
						<af:col property="libsStr" key="project_creation.java.eclipse.lib.libs" />
					</af:cols>
					<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
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
					</sec:ifHasProfile>
				</af:table> 
				<%-- End eclipse libraries--%>
				<br/>
				<br/>
	
				<%-- Advanced options visible only by administrators --%> 
			
				<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME%>" applicationId="<%=applicationId%>">
					<h3>
						<bean:message
							key="project_creation.compiling.java.eclipse.advanced_options.title" />
					</h3>
					<af:dropDownPanel titleKey="buttonTag.menu.aide">
						<bean:message
							key="project_creation.java.eclipse.advanced_options.help"
							arg0='<%=WebMessages.getString(request, "eclipse.compilation.advanced.options")%>' />
					</af:dropDownPanel>
					<br/>
					<table id="advancedOptionsTable" class="formulaire" >
						<thead>
							<tr>
								<td colspan="2">
									<bean:message key="project_creation.java.eclipse.advanced_options" />
								</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<af:field name="jCompilingForm" property="advancedOptions"
									key="project_creation.java.eclipse.advanced_options" size="90" />
							</tr>
						</tbody>
					</table>
				</sec:ifHasProfile>
			</af:dropDownPanel>
			<br/>
			<br/>
		</div>
		</logic:equal>
		<br/>
		<%-- General task configuration button --%>
		<%-- Displayed only if the user is not a reader for the application--%>
		<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
			<jsp:include page="common_parameters_buttons.jsp" />
		</sec:ifHasProfile>
	</af:form>
</logic:notEqual>