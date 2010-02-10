<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<%--
Affiche la liste des nouvelles applications en attente de confirmation
--%>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>



<af:page>
	<af:body>
		<af:canvasCenter titleKey="applications_acknowledged.title">
			<br />
			<br />
			<div style="color:#f00;">
				<html:errors/>
			</div>
			<br/><br/>
			<bean:message key="applications_acknowledged.details" />
			<br />
			<br />
			<%-- Parcours de la liste des applications et affichage de ceux-ci --%>
            <html:messages id="msg" message="true">
             <bean:write name="msg"/><br>
            </html:messages>
		</af:canvasCenter>
	</af:body>
</af:page>