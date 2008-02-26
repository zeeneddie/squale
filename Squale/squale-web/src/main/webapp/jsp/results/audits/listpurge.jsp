<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants" %>

<%--
Affiche la liste audits purgés
--%>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>

<%-- recupere le type de list dans le parametre kind du formulaire --%>
<bean:define name="splitAuditsListForm" property="kind" id="kind"
	type="String" />
<bean:define id="applicationId" name="splitAuditsListForm"
	property="applicationId" />
<%
	// On récupère le lien pour le bouton retour
	String returnLink = (String)request.getAttribute(SqualeWebConstants.RETURN_ACTION_KEY);
	pageContext.setAttribute("returnLink", returnLink);
%>

<af:page>
	<af:body>
		<af:canvasCenter titleKey="audits_purge.title">
			<br />
			<br />
			<ul>
				<html:messages id="message" message="true">
					<li><bean:write name="message" /></li>
				</html:messages>
			</ul>
			<logic:notEmpty name="returnLink">
				<af:buttonBar>
					<af:button name="retour" toolTipKey="toolTip.retour" onclick="<%=returnLink%>"/>
				</af:buttonBar>
			</logic:notEmpty>
		</af:canvasCenter>
	</af:body>
</af:page>