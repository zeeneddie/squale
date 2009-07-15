<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@page import="org.squale.squaleweb.servlet.UserSqualeSessionContext"%>
<bean:define id="SQUALE_ADMIN_DIFFUSION_LIST" name="<%=UserSqualeSessionContext.USER_SQUALE_SESSION_CONTEXT%>" property="squaleAdminsMailingList" type="String"></bean:define>

<div class="menu_action">
	<div style="padding-top:400px;"></div>
	<ul><li><a href="logout.do"  title="Se déconnecter de l'application" style="BACKGROUND-IMAGE:url(theme/charte_v03_001/img/ico/neutre/cross.gif)" ><bean:message key="buttonTag.menu.deconnexion"/></a></li></ul>
	<ul><li><a href="utilLink.do?action=goodPractice&title=qualimetry"  title="Aide sur SQUALE" style="BACKGROUND-IMAGE:url(theme/charte_v03_001/img/ico/neutre/help.gif)" ><bean:message key="buttonTag.menu.aide"/></a></li></ul>
	<ul><li><a href="list_projects.do?action=searchProject&firstCall=true"  title="Rechercher un projet" style="BACKGROUND-IMAGE:url(theme/charte_v03_001/img/ico/neutre/magnif.gif)" ><bean:message key="buttonTag.menu.rechercher"/></a></li></ul>
	<ul><li><a href="mailto:<%=SQUALE_ADMIN_DIFFUSION_LIST %>"  title="Envoyer un mail à la liste de diffusion" style="BACKGROUND-IMAGE:url(theme/charte_v03_001/img/ico/neutre/mail.gif)" ><bean:message key="buttonTag.menu.contact"/></a></li></ul>
	<ul><li><a href="stats.do?action=displayUser"  title="Quelques statistiques" style="BACKGROUND-IMAGE:url(theme/charte_v03_001/img/ico/neutre/tools.gif)" ><bean:message key="buttonTag.menu.stats"/></a></li></ul>
	<%-- Add a JSP fragment filtered during build time, that shows the running Squale version --%>
	<jsp:include page="squale_version_identifier.jsp" flush="true"/>
</div>
