<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.component.AuditForm"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.component.AuditListForm"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentListForm"%>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collections"%>
<%@ page import="com.airfrance.squaleweb.comparator.ComponentComparator"%>

<%
            // On récupère les composants
            Object results[] = (Object[]) request.getSession().getAttribute( SqualeWebConstants.CHILDREN_KEY );
            // les packages
            List packages = ( (ComponentListForm) results[0] ).getList();
            // et les fichiers
            List files = ( (ComponentListForm) results[1] ).getList();
            // On trie les composants par ordre alphabétique
            ComponentComparator cc = new ComponentComparator();
            Collections.sort( packages, cc );
            Collections.sort( files, cc );

            // On ajoute ces listes triées dans la requête
            request.setAttribute( "packages", packages );
            request.setAttribute( "files", files );
            // On définie le titre de la page
            String title = "project.components.title";
            // Ainsi que la clé du message d'aide
            String helpKey = "project.components.details";
            // Et le titre de tabbedPane
            String tabbedPaneTitle = "project.components.packages.tab";
            // car le titre est différent si on affiche les composants exclus
            if ( request.getAttribute( "excluded" ) != null )
            {
                title = "project.components.excluded.title";
                helpKey = "project.components.excluded.details";
                tabbedPaneTitle = "project.components.excluded.tab";
            }

            // Message d'information dans le cas où le nombre de composants est trop important
            String errorMsg =
                (String) request.getSession().getAttribute(
                                                            com.airfrance.squaleweb.applicationlayer.action.results.project.ProjectComponentsAction.TOO_MUCH_COMPONENTS_MSG );
            // On le place dans le contexte si on est dans ce cas
            if ( null != errorMsg )
            {
                pageContext.setAttribute( "errorMsg", errorMsg );
            }
%>

<bean:define id="projectId" name="componentForm" property="projectId"
	type="String" />
<bean:define id="projectName" name="componentForm"
	property="projectName" type="String" />
<bean:define id="currentAuditId" name="componentForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="componentForm"
	property="previousAuditId" type="String" />


<af:page titleKey="<%=title%>">
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<af:canvasCenter>
			<br />
			<squale:resultsHeader name="componentForm" />
			<logic:empty name="excluded">
				<br />
				<h2><bean:message key="project.components.subtitle" /></h2>
			</logic:empty>
			<br />

			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="<%=helpKey%>" />
				<logic:empty name="excluded">
					<br />
					<br />
					<bean:message key="project.components.filter.details" />
				</logic:empty>
			</af:dropDownPanel>

			<br />
			<br />
			<%-- On n'affiche le filtre que si on n'est pas dans les composants exclus--%>
			<logic:empty name="excluded">
				<af:form action="project_component.do">
					<input type="hidden" name="projectId" value="<%=projectId%>">
					<input type="hidden" name="currentAuditId"
						value="<%=currentAuditId%>">
					<input type="hidden" name="previousAuditId"
						value="<%=previousAuditId%>">
					<input type="hidden" name="which" value="components.all">
					<af:dropDownPanel titleKey="reference.filter" expanded="false">
						<table width="50%" class="formulaire" cellpadding="0"
							cellspacing="0" border="0" align="center">
							<tr class="fondClair">
								<td><af:field property="filter"
									key="project.component.filter" /></td>
							</tr>
						</table>
						<af:buttonBar>
							<af:button type="form" name="valider" callMethod="root"
								toolTipKey="toolTip.valider" />
						</af:buttonBar>
					</af:dropDownPanel>
					<br />
				</af:form>
			</logic:empty>
			<%-- 
					On affiche un message d'avertissement indiquant que seulement 1000 composants ont été
					remontés si c'est le cas
				--%>
			<logic:notEmpty name="errorMsg">
				<div style="color: #f00"><bean:write name="errorMsg"
					filter="false" /> <br />
				<br />
				</div>
			</logic:notEmpty>
			<af:tabbedPane name="coponent_root">
				<!-- Packages -->
				<af:tab key="<%=tabbedPaneTitle%>" name="packages"
					lazyLoading="false">
					<af:table name="packages" scope="request">
						<af:cols id="element">
							<af:col property="name" sortable="true"
								href='<%="project_component.do?action=component&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId%>'
								paramName="element" paramId="component" paramProperty="id"
								key="project.component.name" />
							<af:col property="type" sortable="true"
								key="project.component.type">
								<bean:message name="element" property="type" />
							</af:col>
						</af:cols>
					</af:table>
					<br />
					<br />
				</af:tab>
			</af:tabbedPane>
		</af:canvasCenter>
	</af:body>
</af:page>