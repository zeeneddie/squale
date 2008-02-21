<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.AuditForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils" %>
<%@page import="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO"%>

<%--
	Permet de rechercher un projet parmis les applications visibles par l'utilisateur.
--%>

<af:page titleKey="search.title">
	<af:body>
		<af:canvasCenter>
			<br />
			<bean:message key="search.intro" />
			<br />
			<br />
			<af:form action="list_projects.do" method="post">
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<tr class="fondClair">
						<af:field key="search.application_beginning_name"
							property="applicationBeginningName" />
					</tr>
					<tr class="fondClair">
						<af:field key="search.project_beginning_name"
							property="projectBeginningName" />
					</tr>
				</table>
				<af:buttonBar>
					<af:button name="rechercher" toolTipKey="toolTip.search_project"
						callMethod="searchProject" singleSend="true" />
				</af:buttonBar>
				<logic:notEmpty name="searchProjectForm" property="projectForms">
					<br />
					<br />
					<bean:message key="search.projects_found" />
					<br />
					<bean:define name="searchProjectForm" property="projectForms"
						id="projectForms" type="java.util.Map"/>
					<bean:define id="nbProjects" value='<%=""+projectForms.keySet().size()%>'/>
					<br />
					<logic:equal name="nbProjects" value="0">
						<bean:message key="table.results.none" />
					</logic:equal>
					<logic:greaterThan name="nbProjects" value="0">
						<af:table name="searchProjectForm" property="keys"
							emptyKey="table.results.none">
							<af:cols id="project">
								<%
									// On va récupéré le statut de l'audit
									AuditForm audit = (AuditForm) projectForms.get((ProjectForm)project);
									String auditStatus = "";
									if(null != audit) {
										// On rend l'audit accessible
										pageContext.setAttribute("audit", audit);
										// On prend en compte le statut seulement si celui-ci est en échec
										// ou partiel (traitement différent si réussi et pas de traitement si
										// en cours ou supprimé)
										if(audit.getStatus() == AuditBO.FAILED || audit.getStatus() == AuditBO.PARTIAL) {
											auditStatus = ""+audit.getStatus();
										}
									}
									// On rend le statut de l'audit accessible
									pageContext.setAttribute("auditStatus", auditStatus);
								%>
								<bean:define id="applicationId" name="project"
									property="applicationId" />
								<bean:define id="projectId" name="project" property="id" />
								<logic:equal name="project" property="hasTerminatedAudit"
									value="true">
									<af:col key="search.project.tab.name" property="projectName"
										sortable="true" href="project.do?action=select"
										paramName="project" paramId="projectId" paramProperty="id" />
								</logic:equal>
								<logic:equal name="project" property="hasTerminatedAudit"
									value="false">
									<logic:equal name="auditStatus" value="">
										<af:col key="search.project.tab.name" property="projectName"
											sortable="true" />
									</logic:equal>
									<logic:equal name="auditStatus" value='<%=""+AuditBO.PARTIAL%>'>
										<af:col key="search.project.tab.name" property="projectName"
											sortable="true" paramName="audit" paramId="currentAuditId"
											paramProperty="id"
											href='<%="/squale/audits.do?action=select&kind="+auditStatus%>' />
									</logic:equal>
									<logic:equal name="auditStatus" value='<%=""+AuditBO.FAILED%>'>
										<af:col key="search.project.tab.name" property="projectName"
											sortable="true" paramName="audit" paramId="currentAuditId"
											paramProperty="id"
											href='<%="/squale/project_errors.do?action=errors&projectId="+projectId+"&applicationId=" + applicationId%>' />
									</logic:equal>
								</logic:equal>
								<af:col key="search.application.tab.name"
									property="applicationName" sortable="true" />
								<%-- permettant un lien direct vers l'administration du projet --%>
								<af:col key="projects.manage" property="hasTerminatedAudit"><%-- On met une propriété bidon juste pour pas avoir d'erreur --%>
									<a href='<%="/squale/config_project.do?action=selectProjectToModify&applicationId="+applicationId 
											 	+"&projectId="+projectId %>' >
										<bean:message key="project.manage" />
									</a>	
								</af:col>
							</af:cols>
						</af:table>
					</logic:greaterThan>
				</logic:notEmpty>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>