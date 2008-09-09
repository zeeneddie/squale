<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.LogonBean" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="com.airfrance.squaleweb.comparator.ComponentComparator" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils" %>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages" %>

<%List list = ((LogonBean) session.getAttribute(com.airfrance.welcom.struts.util.WConstants.USER_KEY)).getApplicationsList();
Collections.sort(list, new ComponentComparator());
// On récupère Le nombre min d'appli pour réaliser le menu groupé
int nbMinAppli = SqualeWebActionUtils.getApplicationMenuKey(request);
%>
<div id="header" class="ciel04">
<hr class="bg_theme">
<div id="visuel" 
	style="float:left;width:29%;background-image: url(theme/charte_v03_001/img/tetiere/squale-banner-left.gif)">
</div>
<div id="visuel" 
	style="float:right;width:70%;background: right no-repeat;background-image: url(theme/charte_v03_001/img/tetiere/squale-banner-right.gif)">
</div> 
</div>
<div id="navigation" class="bg_theme" style="clear:both"><af:menu>
	<af:menuItem key="menu.homepage" color="2" action="index.do"></af:menuItem>
	<af:menuItem key="menu.applications" color="2">
		<af:menuItem key="menu.view_all_applications" color="2"
			action="all_applications.do?action=listNotPublic"></af:menuItem>
		<!-- Parcours de la liste des applications et affichage de celles-ci -->
		<logic:present
			name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
			scope="session">
			<bean:define id="notPublicList"
				name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
				property="notPublicList" type="List" />
			<%
				// On récupère les clés de configurations pour construire le menu
				// 1. Le nombre min d'appli pour réaliser le menu groupé (défini plus haut car commun aux
				// menu publique
				// 2. La liste des groupes
				String groupList = WebMessages.getString(request, "groupForApplicationsMenu");
				// On récupère le map des groupes alphabétiques pour les applications non publiques
				java.util.SortedMap notPublicMap = LogonBean.getApplicationMenu(notPublicList, groupList, nbMinAppli);
				// si la map est nulle alors c'est qu'il n'y a pas assez d'applications ou qu'il y a un pb de configuration
				if(null != notPublicMap) {
					pageContext.setAttribute("notPublicMap", notPublicMap);
				}
			%>
			<logic:empty name="notPublicMap">
				<logic:iterate id="appli"
					name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
					property="notPublicList"
					type="com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm"
					scope="session">
					<logic:equal name="appli" property="hasResults" value="true">
						<af:menuItem key="<%=appli.getApplicationName()%>" color="2"
							action="<%=\"application.do?action=summary&applicationId=\"+appli.getId()%>"></af:menuItem>
					</logic:equal>
				</logic:iterate>
			</logic:empty>
			<logic:notEmpty name="notPublicMap">
				<logic:iterate id="mapId" name="notPublicMap">
					<bean:define id="group" name="mapId" property="key" type="String" />
					<af:menuItem key="<%=group%>" color="2">
						<logic:iterate id="appli" name="mapId" property="value"
							type="com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm">
							<af:menuItem key="<%=appli.getApplicationName()%>" color="2"
								action="<%=\"application.do?action=summary&applicationId=\"+appli.getId()%>"></af:menuItem>
						</logic:iterate>
					</af:menuItem>
				</logic:iterate>
			</logic:notEmpty>
		</logic:present>
		<%-- sous-menu des applications publiques --%>
		<af:menuItem key="menu.public_applications" color="2">
			<af:menuItem key="menu.view_all_public_applications" color="2"
				action="all_applications.do?action=listPublic" />
			<!-- Parcours de la liste des applications publiques et affichage de celles-ci -->
			<logic:present
				name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
				scope="session">
				<bean:define id="publicList"
					name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
					property="publicList" type="List" />
				<%
					// On récupére la liste des groupes pour les applications PUBLIQUES
					String groupList = WebMessages.getString(request, "groupForPublicApplicationsMenu");
					// On récupère le map des groupes alphabétiques pour les applications publiques
					java.util.SortedMap publicMap = LogonBean.getApplicationMenu(publicList, groupList, nbMinAppli);
					// si la map est nulle alors c'est qu'il n'y a pas assez d'applications ou qu'il y a un pb de configuration
					if(null != publicMap) {
						pageContext.setAttribute("publicMap", publicMap);
					}
				%>
				<logic:empty name="publicMap">
					<logic:iterate id="appli"
						name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
						property="publicList"
						type="com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm"
						scope="session">
						<logic:equal name="appli" property="hasResults" value="true">
							<af:menuItem key="<%=appli.getApplicationName()%>" color="2"
								action="<%=\"application.do?action=summary&applicationId=\"+appli.getId()%>"></af:menuItem>
						</logic:equal>
					</logic:iterate>
				</logic:empty>
				<logic:notEmpty name="publicMap">
					<logic:iterate id="mapId" name="publicMap">
						<bean:define id="group" name="mapId" property="key" type="String" />
						<af:menuItem key="<%=group%>" color="2">
							<logic:iterate id="appli" name="mapId" property="value"
								type="com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm">
								<af:menuItem key="<%=appli.getApplicationName()%>" color="2"
									action="<%=\"application.do?action=summary&applicationId=\"+appli.getId()%>"></af:menuItem>
							</logic:iterate>
						</af:menuItem>
					</logic:iterate>
				</logic:notEmpty>
			</logic:present>
		</af:menuItem>
	</af:menuItem>
	<af:menuItem key="menu.new_application" color="2"
		action="utilLink.do?action=newApplication"></af:menuItem>
	<af:menuItem key="menu.reference" color="2"
		action="referenceList.do?action=list"></af:menuItem>
	<af:menuItem key="menu.administration" color="2">
		<af:menuItem key="menu.administration.account" color="2"
			action="display_account.do?action=detail" />
		<af:menuItem key="menu.administration.applications" color="2"
			action="utilLink.do?action=adminApplication" />
		<af:menuItem key="menu.administration.newapplications" color="2"
			action="ack_newapplication.do?action=list" accessKey="admin" />
		<af:menuItem key="menu.administration.audits" color="2">
			<af:menuItem key="menu.administration.not_attempted_audit" color="2"
				action="adminNotAttemptedAudit.do?action=displayNotAttemptedAndRunning"
				accessKey="admin" />
			<af:menuItem key="menu.administration.terminated_audits" color="2"
				action="audits.do?action=displayTerminated"
				accessKey="admin" />
			<af:menuItem key="menu.administration.failed_audits" color="2"
				action="auditsFailed.do?action=displayFailedOrPartial"
				accessKey="admin" />
		</af:menuItem>
		<af:menuItem key="menu.administration.communication" color="2">
			<af:menuItem key="menu.administration.news" color="2"
				action="adminNews.do?action=listNews&which=current"
				accessKey="admin" />
			<af:menuItem key="menu.sendMail" color="2"
				action="sendMail.do?action=init&fromMenu=true" accessKey="admin" />
		</af:menuItem>
		<af:menuItem key="menu.administration.grids" color="2"
			action="grid.do?action=list" accessKey="admin" />
		<af:menuItem key="menu.administration.tools" color="2"
			accessKey="admin">
			<af:menuItem key="menu.administration.CppTest" color="2"
				action="cppTest.do?action=list" accessKey="admin" />
			<af:menuItem key="menu.administration.pmd" color="2"
				action="pmd.do?action=list" accessKey="admin" />
			<af:menuItem key="menu.administration.checkstyle" color="2"
				action="checkstyle.do?action=list" accessKey="admin" />
			<af:menuItem key="menu.administration.portail" color="2"
				action="loadConfig.do" accessKey="admin" />
		</af:menuItem>
		<af:menuItem key="menu.administration.stats" color="2"
			accessKey="admin">
			<af:menuItem key="menu.administration.roi" color="2"
				action="roi.do?action=display" accessKey="admin" />
			<af:menuItem key="menu.administration.stats.general" color="2"
				action="stats.do?action=displayAdmin" accessKey="admin" />
		</af:menuItem>
		<af:menuItem key="menu.administration.messages" color="2"
			action="utilLink.do?action=message" accessKey="admin" />
	</af:menuItem>
</af:menu></div>